package cn.sdt.ottadvert.db.bean;

import android.text.TextUtils;

/**
 * Created by SDT13411 on 2018/3/5.
 */

public class DefaultMaterial {

    protected int id;
    protected int width;
    protected int height;
    protected String materialUrl; //素材下载地址
    protected String md5;
    protected String spaceCode;
    protected int materialType;
    protected int duration;
    protected String materialPath;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSpaceCode() {
        return spaceCode;
    }

    public void setSpaceCode(String spaceCode) {
        this.spaceCode = spaceCode;
    }

    public int getMaterialType() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType = materialType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMaterialPath() {
        return materialPath;
    }

    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    //默认素材相等的逻辑
    //下载url，md5 宽高，全部相同 才认为素材没发生变化
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof DefaultMaterial) {
            DefaultMaterial material = (DefaultMaterial) obj;
            if (obj == this)
                return true;
            if (TextUtils.isEmpty(material.materialUrl)
                    || TextUtils.isEmpty(material.md5))
                return false;
            return material.materialUrl.equals(this.materialUrl)
                    && material.md5.equals(this.md5)
                    && material.width == this.width
                    && material.height == this.height;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + width;
        result = 31 * result + height;
        if (md5 != null) {
            result += md5.hashCode();
        }
        if (materialUrl != null) {
            result += materialUrl.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("[")
                .append(id).append("|")
                .append(width).append("|")
                .append(height).append("|")
                .append(materialType).append("|").append("\n")
                .append(md5).append("|").append("\n")
                .append(materialUrl).append("|").append("\n")
                .append(materialPath)
                .append("]");
        return stringBuffer.toString();

    }


}
