package cn.sdt.ottadvert.db.bean;

/**
 * Created by SDT13292 on 2017/4/26.
 */
public class Material extends DefaultMaterial {

    protected int scheduleId;
    protected int materialId;
    protected int actionType;
    protected String action;
    protected String monitorUrl;               //监控url，统计用 ,可能有多个，所以需要将String转换位List<String>
    protected long startTime;
    protected long endTime;
    protected String orderNo;
    protected int deliverySpeed;                       //素材的优先级
    protected int deliveryLimit;                        //频率,每日最多能播放的次数
    protected int playCount;                            //实际播放次数  ，已经播放的次数
    protected String isCurrent = "N";           //是否是当前正准备播放的广告  Y/N

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int id) {
        this.materialId = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getMaterialType() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    @Override
    public String getMaterialPath() {
        return materialPath;
    }

    @Override
    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getDeliverySpeed() {
        return deliverySpeed;
    }

    public void setDeliverySpeed(int deliverySpeed) {
        this.deliverySpeed = deliverySpeed;
    }

    public int getDeliveryLimit() {
        return deliveryLimit;
    }

    public void setDeliveryLimit(int deliveryLimit) {
        this.deliveryLimit = deliveryLimit;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrentPlayMaterial) {
        this.isCurrent = isCurrentPlayMaterial;
    }

    public boolean isCurrent() {
        return "Y".equals(isCurrent);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Material) {
            Material tableMaterial = (Material) obj;
            return tableMaterial.getScheduleId() == this.getScheduleId()
                    && tableMaterial.getMaterialId() == this.getMaterialId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + scheduleId;
        result = result * 31 + materialId;
        return result;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[\n")
                .append("scheduleId:").append(this.scheduleId).append(",\n")
                .append("materialId:").append(this.materialId).append(",\n")
                .append("materialUrl:").append(this.materialUrl).append(",\n")
                .append("materialPath:").append(this.materialPath).append("\n]");
        return stringBuffer.toString();
    }
}
