package com.lly.model;

/**
 * @Author: WangLei
 * @Description: 公告模板类
 * @Date: Create in 2018/3/13 8:29
 * @Modify by WangLei
 */
public class NewsModel extends BaseModel {

    private String newsId;
    private String newsTitle;
    private String newsContent;
    private String newsAuthor;
    private boolean newsShow;
    private String newsShowName;
    private String showTime;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public boolean getNewsShow() {
        return newsShow;
    }

    public void setNewsShow(boolean newsShow) {
        this.newsShow = newsShow;
    }

    public String getNewsShowName() {
        return newsShowName;
    }

    public void setNewsShowName(String newsShowName) {
        this.newsShowName = newsShowName;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
}
