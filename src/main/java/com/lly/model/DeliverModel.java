package com.lly.model;

import java.util.List;

public class DeliverModel extends BaseModel {

    private String deliverId;
    private String deliverType;
    private String deliverTypeName;
    private String contractCode;
    private String contractTypeCode;
    private String contractTypeName;
    private String customerName;
    private String deliverDate;
    private String warrantyDate;
    private String deliverAddress;
    private String businessUserId;
    private String businessUserName;
    private String deliverRemark;
    private String deliverStatusCode;
    private String deliverStatusName;
    private String deliverNo;
    private String contractWarranty;
    private String contractAmount;
    private String contractNewCode;
    private String contractBeginTime;
    private String contractEndTime;
    private List<DeliverGoodsModel> goodsModelList;
    private String goodsModelString;
    private String optType;
    private Boolean finishFlag;

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public String getDeliverTypeName() {
        return deliverTypeName;
    }

    public void setDeliverTypeName(String deliverTypeName) {
        this.deliverTypeName = deliverTypeName;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractTypeCode() {
        return contractTypeCode;
    }

    public void setContractTypeCode(String contractTypeCode) {
        this.contractTypeCode = contractTypeCode;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getBusinessUserId() {
        return businessUserId;
    }

    public void setBusinessUserId(String businessUserId) {
        this.businessUserId = businessUserId;
    }

    public String getBusinessUserName() {
        return businessUserName;
    }

    public void setBusinessUserName(String businessUserName) {
        this.businessUserName = businessUserName;
    }

    public String getDeliverRemark() {
        return deliverRemark;
    }

    public void setDeliverRemark(String deliverRemark) {
        this.deliverRemark = deliverRemark;
    }

    public String getDeliverStatusCode() {
        return deliverStatusCode;
    }

    public void setDeliverStatusCode(String deliverStatusCode) {
        this.deliverStatusCode = deliverStatusCode;
    }

    public String getDeliverStatusName() {
        return deliverStatusName;
    }

    public void setDeliverStatusName(String deliverStatusName) {
        this.deliverStatusName = deliverStatusName;
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

    public String getContractBeginTime() {
        return contractBeginTime;
    }

    public void setContractBeginTime(String contractBeginTime) {
        this.contractBeginTime = contractBeginTime;
    }

    public String getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(String contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public List<DeliverGoodsModel> getGoodsModelList() {
        return goodsModelList;
    }

    public void setGoodsModelList(List<DeliverGoodsModel> goodsModelList) {
        this.goodsModelList = goodsModelList;
    }

    public String getGoodsModelString() {
        return goodsModelString;
    }

    public void setGoodsModelString(String goodsModelString) {
        this.goodsModelString = goodsModelString;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public Boolean getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(Boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

}
