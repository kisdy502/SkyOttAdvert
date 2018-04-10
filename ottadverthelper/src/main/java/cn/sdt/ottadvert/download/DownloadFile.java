package cn.sdt.ottadvert.download;

/**
 * Created by Administrator on 2018/4/10.
 */

public class DownloadFile {

    protected String fileName;      //文件名
    protected String url;           //文件url
    protected String filePath;      //文件下载目录,非全路径

    public DownloadFile(String url) {
        this.url = url;
    }

    public DownloadFile(String filePath, String url) {
        this.filePath = filePath;
        this.url = url;
    }

    public DownloadFile(String url, String filePath, String fileName) {
        this.url = url;
        this.filePath = filePath;
        this.fileName = fileName;
    }


    public String getFileName() {
        return fileName;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
