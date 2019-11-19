package com.lly.model;

public class PageModel extends BaseModel {

    private String pageId;
    private String pageCode;
    private String pageUrl;
    private String pageNameEn;
    private String pageNameCn;
    private String pageRemark;
    private boolean pageChecked;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageNameEn() {
        return pageNameEn;
    }

    public void setPageNameEn(String pageNameEn) {
        this.pageNameEn = pageNameEn;
    }

    public String getPageNameCn() {
        return pageNameCn;
    }

    public void setPageNameCn(String pageNameCn) {
        this.pageNameCn = pageNameCn;
    }

    public String getPageRemark() {
        return pageRemark;
    }

    public void setPageRemark(String pageRemark) {
        this.pageRemark = pageRemark;
    }

    public boolean getPageChecked() {
        return pageChecked;
    }

    public void setPageChecked(boolean pageChecked) {
        this.pageChecked = pageChecked;
    }

}
