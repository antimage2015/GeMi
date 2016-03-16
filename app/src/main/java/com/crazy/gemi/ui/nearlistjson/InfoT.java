package com.crazy.gemi.ui.nearlistjson;

import java.util.List;

public class InfoT {

    private PageInfoT pageInfo;
    private List<MerchantKeyT> merchantKey;

    public List<MerchantKeyT> getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(List<MerchantKeyT> merchantKey) {
        this.merchantKey = merchantKey;
    }

    public PageInfoT getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoT pageInfo) {
        this.pageInfo = pageInfo;
    }
}

