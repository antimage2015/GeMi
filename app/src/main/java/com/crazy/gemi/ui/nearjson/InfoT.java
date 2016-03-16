package com.crazy.gemi.ui.nearjson;


import com.warmtel.expandtab.KeyValueBean;

import java.util.List;

public class InfoT {

    private List<AreaKeyInfo> areaKey;
    private List<KeyValueBean> distanceKey;
    private List<KeyValueBean> industryKey;
    private List<KeyValueBean> sortKey;

    public List<AreaKeyInfo> getAreaKey() {
        return areaKey;
    }

    public void setAreaKey(List<AreaKeyInfo> areaKey) {
        this.areaKey = areaKey;
    }

    public List<KeyValueBean> getDistanceKey() {
        return distanceKey;
    }

    public void setDistanceKey(List<KeyValueBean> distanceKey) {
        this.distanceKey = distanceKey;
    }

    public List<KeyValueBean> getIndustryKey() {
        return industryKey;
    }

    public void setIndustryKey(List<KeyValueBean> industryKey) {
        this.industryKey = industryKey;
    }

    public List<KeyValueBean> getSortKey() {
        return sortKey;
    }

    public void setSortKey(List<KeyValueBean> sortKey) {
        this.sortKey = sortKey;
    }

}
