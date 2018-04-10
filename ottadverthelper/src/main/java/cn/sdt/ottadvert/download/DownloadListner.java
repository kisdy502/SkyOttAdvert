package cn.sdt.ottadvert.download;

/**
 * Created by Administrator on 2018/4/10.
 */

public interface DownloadListner {
    void onProgress(DownloadFile downloadFile, float progress);
    void onCanceled(DownloadFile downloadFile);
    void onPaused(DownloadFile downloadFile);
    void onFinished(DownloadFile downloadFile);
    void onErrored(DownloadFile downloadFile);
}
