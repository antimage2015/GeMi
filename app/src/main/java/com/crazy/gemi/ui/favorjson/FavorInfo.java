package com.crazy.gemi.ui.favorjson;


import java.util.List;

public class FavorInfo {

    private List<FavorMainKey> mainKey;
    private List<FavorIndustryKey> industryKey;

    public List<FavorMainKey> getMainKey() {
        return mainKey;
    }

    public void setMainKey(List<FavorMainKey> mainKey) {
        this.mainKey = mainKey;
    }

    public List<FavorIndustryKey> getIndustryKey() {
        return industryKey;
    }

    public void setIndustryKey(List<FavorIndustryKey> industryKey) {
        this.industryKey = industryKey;
    }
}
