package cn.sdt.ottadvert.db.bean;



/**
 * 上一次素材播放的时间和类型，用来处理跨天素材播放次数清零
 * Created by SDT13411 on 2017/11/6.
 */

public class LastPlayDate {

    int id;
    long lastPlayTimestamp;
    int scheduleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastPlayTimestamp() {
        return lastPlayTimestamp;
    }

    public void setLastPlayTimestamp(long lastPlayTimestamp) {
        this.lastPlayTimestamp = lastPlayTimestamp;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }
}
