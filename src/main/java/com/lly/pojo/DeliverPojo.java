package com.lly.pojo;


import java.sql.Timestamp;

public class DeliverPojo extends BasePojo {

    private long deliverId;
    private int deliverType;
    private String contractCode;
    private ParamPojo contractType;
    private String customerName;
    private Timestamp deliverDate;
    private Timestamp warrantyDate;
    private String deliverAddress;
    private UserPojo businessUser;
    private String deliverRemark;
    private DeliverStatusPojo deliverStatus;
    private String deliverNo;
    private String contractWarranty;
    private String contractAmount;
    private String contractNewCode;
    private Timestamp contractBeginTime;
    private Timestamp contractEndTime;

    public long getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(long deliverId) {
        this.deliverId = deliverId;
    }

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public ParamPojo getContractType() {
        return contractType;
    }

    public void setContractType(ParamPojo contractType) {
        this.contractType = contractType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Timestamp getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Timestamp deliverDate) {
        this.deliverDate = deliverDate;
    }

    public Timestamp getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(Timestamp warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public UserPojo getBusinessUser() {
        return businessUser;
    }

    public void setBusinessUser(UserPojo businessUser) {
        this.businessUser = businessUser;
    }

    public String getDeliverRemark() {
        return deliverRemark;
    }

    public void setDeliverRemark(String deliverRemark) {
        this.deliverRemark = deliverRemark;
    }

    public DeliverStatusPojo getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(DeliverStatusPojo deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo;
    }

    public String getContractWarranty() {
        return contractWarranty;
    }

    public void setContractWarranty(String contractWarranty) {
        this.contractWarranty = contractWarranty;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractNewCode() {
        return contractNewCode;
    }

    public void setContractNewCode(String contractNewCode) {
        this.contractNewCode = contractNewCode;
    }

    public Timestamp getContractBeginTime() {
        return contractBeginTime;
    }

    public void setContractBeginTime(Timestamp contractBeginTime) {
        this.contractBeginTime = contractBeginTime;
    }

    public Timestamp getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Timestamp contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

}
