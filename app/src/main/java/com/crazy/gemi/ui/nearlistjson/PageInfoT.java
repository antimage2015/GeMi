package com.crazy.gemi.ui.nearlistjson;

import java.util.List;

public class PageInfoT {

    private String total;
    private String pageSize;
    private String lastPageNumber;
    private String nowPage;
    private String currNum;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getLastPageNumber() {
        return lastPageNumber;
    }

    public void setLastPageNumber(String lastPageNumber) {
        this.lastPageNumber = lastPageNumber;
    }

    public String getNowPage() {
        return nowPage;
    }

    public void setNowPage(String nowPage) {
        this.nowPage = nowPage;
    }

    public String getCurrNum() {
        return currNum;
    }

    public void setCurrNum(String currNum) {
        this.currNum = currNum;
    }
}