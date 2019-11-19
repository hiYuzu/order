package com.tcb.pojo;

import com.tcb.util.DateUtil;

import java.sql.Timestamp;

/**
 *
 * 日期转换工具
 * @author 王垒
 */
public class BasePojo {

    /**
     * 查询个数
     */
    private int rowCount;
    /**
     * 开始查询位置
     */
    private int rowIndex;

    /**
     * 操作用户ID
     */
    private int optUserId;

    /**
     * 操作用户名称
     */
    private String optUserName;

    /**
     * 操作时间
     */
    private Timestamp optTime = DateUtil.GetSystemDateTime(0);

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

    public int getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(int optUserId) {
        this.optUserId = optUserId;
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    public Timestamp getOptTime() {
        return optTime;
    }

    public void setOptTime(Timestamp optTime) {
        this.optTime = optTime;
    }
}
