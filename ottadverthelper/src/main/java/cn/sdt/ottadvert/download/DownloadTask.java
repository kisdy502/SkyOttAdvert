package cn.sdt.ottadvert.download;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/10.
 */

public class DownloadTask extends Handler {
    private final static String TAG = "dtask";
    private final int THREAD_COUNT = 4;//下载线程数量
    private DownloadFile mDownloadFile;
    private long mFileLength;//文件大小

    private boolean isDownloading = false;//是否正在下载

    private int childCanleCount;//子线程取消数量
    private int childPauseCount;//子线程暂停数量
    private int childFinishCount;//子线程完成下载数量

    private HttpUtil mHttpUtil;//http网络通信工具

    private long[] mProgress;//各个子线程下载进度集合

    private File[] mCacheFiles;//各个子线程下载缓存数据文件

    private File mTmpFile;//临时占位文件

    private boolean pause;//是否暂停
    private boolean cancel;//是否取消下载

    private final static int minDiff = 300;
    private long prevMsgSendTime = 0;


    private final int MSG_PROGRESS = 1;     //进度
    private final int MSG_FINISH = 2;       //完成下载
    private final int MSG_PAUSE = 3;        //暂停
    private final int MSG_CANCEL = 4;       //取消
    private final int MSG_ERROR = 5;       //下载出错

    private DownloadListner mListner;//下载回调监听

    public DownloadTask(DownloadFile point, DownloadListner l) {
        this.mDownloadFile = point;
        this.mListner = l;
        this.mProgress = new long[THREAD_COUNT];
        this.mCacheFiles = new File[THREAD_COUNT];
        this.mHttpUtil = HttpUtil.getInstance();
    }


    public synchronized void start() {
        if (isDownloading)
            return;
        isDownloading = true;

        try {
            mHttpUtil.getContentLength(mDownloadFile.getUrl(), new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    isDownloading = false;
                    sendEmptyMessage(MSG_ERROR);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() != 200) {
                        close(response.body());
                        resetStutus();
                        sendEmptyMessage(MSG_ERROR);
                        return;
                    }
                    // 获取资源大小
                    mFileLength = response.body().contentLength();
                    close(response.body());
                    // 在本地创建一个与资源同样大小的文件来占位
                    mTmpFile = new File(mDownloadFile.getFilePath(), mDownloadFile.getFileName() + ".tmp");
                    Log.d(TAG, "mTmpFile:" + mTmpFile);
                    if (!mTmpFile.getParentFile().exists())
                        mTmpFile.getParentFile().mkdirs();
                    RandomAccessFile tmpAccessFile = new RandomAccessFile(mTmpFile, "rw");
                    tmpAccessFile.setLength(mFileLength);
                     /*将下载任务分配给每个线程*/
                    long blockSize = mFileLength / THREAD_COUNT;// 计算每个线程理论上下载的数量.
                      /*为每个线程配置并分配任务*/
                    for (int threadId = 0; threadId < THREAD_COUNT; threadId++) {
                        long startIndex = threadId * blockSize; // 线程开始下载的位置
                        long endIndex = (threadId + 1) * blockSize - 1; // 线程结束下载的位置
                        if (threadId == (THREAD_COUNT - 1)) { // 如果是最后一个线程,将剩下的文件全部交给这个线程完成
                            endIndex = mFileLength - 1;
                        }
                        Log.d(TAG, "start:" + startIndex + ",end:" + endIndex);
                        download(startIndex, endIndex, threadId);// 开启线程下载
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            resetStutus();
        }
    }

    private void download(final long startIndex, final long endIndex, final int threadId) throws NumberFormatException, IOException {
        long newStartIndex = startIndex;
        final File cacheFile = new File(mDownloadFile.getFilePath(),
                "thread" + threadId + "_" + mDownloadFile.getFileName() + ".cache");
        Log.d(TAG, "cache file:" + cacheFile.getAbsolutePath());
        mCacheFiles[threadId] = cacheFile;
        final RandomAccessFile cacheAccessFile = new RandomAccessFile(cacheFile, "rwd");
        if (cacheFile.exists()) {       // 如果文件存在,可能已经下载了一部分，那么起点重新设置
            String startIndexStr = cacheAccessFile.readLine();
            if (!TextUtils.isEmpty(startIndexStr)) {
                newStartIndex = Integer.parseInt(startIndexStr);//重新设置下载起点
            }
        }
        final long finalStartIndex = newStartIndex;
        mHttpUtil.downloadFileByRange(mDownloadFile.getUrl(), finalStartIndex, endIndex, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                isDownloading = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 206) {// 206：请求部分资源成功码，表示服务器支持断点续传
                    resetStutus();
                    return;
                }
                InputStream is = response.body().byteStream();// 获取流
                RandomAccessFile tmpAccessFile = new RandomAccessFile(mTmpFile, "rw");// 获取前面已创建的文件.

                tmpAccessFile.seek(finalStartIndex);// 文件写入的开始位置.
                   /*  将网络流中的文件写入本地*/
                byte[] buffer = new byte[1024 << 2];
                int length = -1;
                int total = 0;// 记录本次下载文件的大小
                long progress = 0;
                while ((length = is.read(buffer)) > 0) {//读取流
                    if (cancel) {
                        close(cacheAccessFile, is, response.body());//关闭资源
                        cleanFile(cacheFile);//删除对应缓存文件
                        sendEmptyMessage(MSG_CANCEL);
                        return;
                    }
                    if (pause) {
                        //关闭资源
                        close(cacheAccessFile, is, response.body());
                        //发送暂停消息
                        sendEmptyMessage(MSG_PAUSE);
                        return;
                    }
                    tmpAccessFile.write(buffer, 0, length);
                    total += length;
                    progress = finalStartIndex + total;
                    cacheAccessFile.seek(0);
                    cacheAccessFile.write((progress + "").getBytes("UTF-8"));
                    //发送进度消息
                    mProgress[threadId] = progress - startIndex;
                    sendEmptyMessage(MSG_PROGRESS);
                }
                //关闭资源
                close(cacheAccessFile, is, response.body());
                // 删除临时文件
                cleanFile(cacheFile);
                //发送完成消息
                sendEmptyMessage(MSG_FINISH);
            }
        });
    }


    private void close(Closeable... closeables) {
        if (closeables != null) {
            int length = closeables.length;
            try {
                for (int i = 0; i < length; i++) {
                    Closeable closeable = closeables[i];
                    if (null != closeable)
                        closeables[i].close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                for (int i = 0; i < length; i++) {
                    closeables[i] = null;
                }
            }
        }
    }

    /**
     * 重置下载状态
     */
    private void resetStutus() {
        pause = false;
        cancel = false;
        isDownloading = false;
    }

    /**
     * 删除临时文件
     */
    private void cleanFile(File... files) {
        for (int i = 0, length = files.length; i < length; i++) {
            if (null != files[i])
                files[i].delete();
        }
    }

    @Override
    public void handleMessage(Message msg) {
        if (null == mListner) {
            return;
        }
        switch (msg.what) {
            case MSG_PROGRESS://进度
                long progress = 0;
                for (int i = 0, length = mProgress.length; i < length; i++) {
                    progress += mProgress[i];
                }
                long current = System.currentTimeMillis();
                long diff = current - prevMsgSendTime;
                if (diff > minDiff) {
                    mListner.onProgress(mDownloadFile, progress * 1.0f / mFileLength);
                    prevMsgSendTime = current;
                }
                break;
            case MSG_PAUSE://暂停
                childPauseCount++;
                if (childPauseCount % THREAD_COUNT != 0)
                    return;//等待所有的线程完成暂停，真正意义的暂停，以下同理
                resetStutus();
                mListner.onPaused(mDownloadFile);
                break;
            case MSG_CANCEL://取消
                childCanleCount++;
                if (childCanleCount % THREAD_COUNT != 0) return;
                resetStutus();
                mProgress = new long[THREAD_COUNT];
                mListner.onCanceled(mDownloadFile);
                break;
            case MSG_FINISH://完成
                childFinishCount++;
                if (childFinishCount % THREAD_COUNT != 0) return;
                mTmpFile.renameTo(new File(mDownloadFile.getFilePath(), mDownloadFile.getFileName()));   //下载完毕后，重命名目标文件名
                resetStutus();
                mListner.onFinished(mDownloadFile);
                break;
            case MSG_ERROR:
                mListner.onErrored(mDownloadFile);
                break;

        }
    }
}
