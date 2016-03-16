package com.crazy.gemi.ui.favorjson;


public class FavorIndustryKey {

    private String industryPic;       // 大图标
    private String iconUrl;           // 小图标（有时不能不能加载）
    private String industry;          // 描述（如：美食，推荐等）

    public String getIndustryPic() {
        return industryPic;
    }

    public void setIndustryPic(String industryPic) {
        this.industryPic = industryPic;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
