package com.lly.pojo;

/**
 * @Author: WangLei
 * @Description: 系统页面POJO
 * @Date: Create in 2018/3/8 11:36
 * @Modify by WangLei
 */
public class PagePojo extends BasePojo {

    private int pageId;
    private String pageCode;
    private String pageUrl;
    private String pageNameEn;
    private String pageNameCn;
    private String pageRemark;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
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

}
