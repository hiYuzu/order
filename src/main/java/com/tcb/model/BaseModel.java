package com.tcb.model;

/**
 * @Author: WangLei
 * @Description: 页面模板基类
 * @Date: Create in 2018/1/25 13:35
 * @Modify by WangLei
 */
public class BaseModel {

    /**
     * 操作者ID
     */
    private String optUserId;
    /**
     * 操作者名称
     */
    private String optUserName;
    /**
     * 操作时间
     */
    private String optTime;
    /**
     * 当前页
     */
    private int page;
    /**
     * 每页行数
     */
    private int limit;
    /**
     * 查询个数
     */
    private int rowCount;
    /**
     * 开始查询位置
     */
    private int rowIndex;

    public String getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(String optUserId) {
        this.optUserId = optUserId;
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int rows) {
        this.limit = rows;
        this.rowCount = this.limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        setRowIndex();
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setRowIndex() {
        if (this.page == 0) {
            this.page = 1;
        }
        this.rowIndex = (this.page - 1) * this.limit;
    }

}
