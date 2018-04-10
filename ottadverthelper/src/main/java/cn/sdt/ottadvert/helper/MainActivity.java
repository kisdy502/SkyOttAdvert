package cn.sdt.ottadvert.helper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.sdt.ottadvert.download.DownloadFile;
import cn.sdt.ottadvert.download.DownloadListner;
import cn.sdt.ottadvert.download.DownloadTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String rootPath = getExternalCacheDir().getAbsolutePath();
        Log.d(TAG, "root:" + rootPath);

        List<DownloadFile> fileList = new ArrayList<>();
        fileList.add(new DownloadFile("http://apk.beemarket.tv/AV/11_21845171_73083.beemarket", rootPath, "aiqiyi"));
        fileList.add(new DownloadFile("http://apk.beemarket.tv/AV/156_21498277_30203.beemarket", rootPath, "beevideo"));
        fileList.add(new DownloadFile("http://apk.beemarket.tv/AV/21622829_593504.beemarket", rootPath, "youkuvideo"));
        fileList.add(new DownloadFile("http://apk.beemarket.tv/GAME/11690716_611.beemarket", rootPath, "JJdizhu"));

        DownloadTask task = new DownloadTask(fileList.get(0), new DownloadListner() {
            @Override
            public void onProgress(DownloadFile downloadFile, float progress) {
                Log.d(TAG, downloadFile.getFileName() + ",progress:" + progress);
            }

            @Override
            public void onCanceled(DownloadFile downloadFile) {
                Log.d(TAG, downloadFile.getFileName() + ":onCanceled");
            }

            @Override
            public void onPaused(DownloadFile downloadFile) {
                Log.d(TAG, downloadFile.getFileName() + ":onPaused");
            }

            @Override
            public void onFinished(DownloadFile downloadFile) {
                Log.d(TAG, downloadFile.getFileName() + ":downloaded");
            }

            @Override
            public void onErrored(DownloadFile downloadFile) {
                Log.d(TAG, downloadFile.getFileName() + ":onErrored");
            }
        });
        task.start();
    }

}
