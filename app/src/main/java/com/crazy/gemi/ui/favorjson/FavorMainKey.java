package com.crazy.gemi.ui.favorjson;


public class FavorMainKey {

    private String picUrl;         // 小图片（较长，叫窄）
    private String bigpicUrl;      // 大图片(长宽适中)
    private String description;    // 描述（简介）
    private String desc;           // 描述（具体）
    private String restNum;        // 库存的数量

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBigpicUrl() {
        return bigpicUrl;
    }

    public void setBigpicUrl(String bigpicUrl) {
        this.bigpicUrl = bigpicUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRestNum() {
        return restNum;
    }

    public void setRestNum(String restNum) {
        this.restNum = restNum;
    }
}
