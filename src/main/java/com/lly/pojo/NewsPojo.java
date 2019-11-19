package com.lly.pojo;

/**
 * @Author: WangLei
 * @Description: 公告POJO
 * @Date: Create in 2018/3/12 15:22
 * @Modify by WangLei
 */
public class NewsPojo extends BasePojo {

    private long newsId;
    private String newsTitle;
    private String newsContent;
    private String newsAuthor;
    private boolean newsShow;
    private String showTime;

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
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

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

}
