package cn.sdt.ottadvert.db.bean;


/**
 * Created by SDT13411 on 2017/12/28.
 */
public class ErrorMD5Material {

    protected int id;
    protected int scheduleId;
    protected int materialId;
    protected String md5;
    protected String materialUrl;
    private long createTimestamp;

    public int getId() {
        return id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public void setId(int id) {
        this.id = id;
    }
}
