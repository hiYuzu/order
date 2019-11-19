package com.tcb.model;

/**
 * @Author: WangLei
 * @Description: 发货单状态模板类
 * @Date: Create in 2019/1/24 14:57
 * @Modify by WangLei
 */
public class DeliverStatusModel extends BaseModel {

    private String statusId;
    private String statusCode;
    private String statusName;
    private String statusRemark;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
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
