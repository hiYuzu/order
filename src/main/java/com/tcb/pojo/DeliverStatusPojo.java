package com.tcb.pojo;

/**
 * @Author: WangLei
 * @Description: 发货单状态POJO
 * @Date: Create in 2018/2/9 14:41
 * @Modify by WangLei
 */
public class DeliverStatusPojo {

    private int statusId;
    private String statusCode;
    private String statusName;
    private String statusRemark;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark;
    }

}
