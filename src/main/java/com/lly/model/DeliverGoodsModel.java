package com.lly.model;

public class DeliverGoodsModel extends BaseModel {

    private String goodsId;
    private String deliverId;
    private String assembleId;
    private String pnCode;
    private String pnName;
    private String snCode;
    private String installEnterprise;
    private String jobNo;
    private String analyzerNumber;
    private String goodsAmount;
    private boolean goodsStatus;
    private String goodsStatusName;
    private String goodsMemo;
    private String returnMemo;
    //外购相关
    private String goodsName;
    private String purchaseContract;
    private String storageDate;
    private String goodsSupplier;
    private String deliverPoint;
    private String warrantyClause;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getAssembleId() {
        return assembleId;
    }

    public void setAssembleId(String assembleId) {
        this.assembleId = assembleId;
    }

    public String getPnCode() {
        return pnCode;
    }

    public void setPnCode(String pnCode) {
        this.pnCode = pnCode;
    }

    public String getPnName() {
        return pnName;
    }

    public void setPnName(String pnName) {
        this.pnName = pnName;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getInstallEnterprise() {
        return installEnterprise;
    }

    public void setInstallEnterprise(String installEnterprise) {
        this.installEnterprise = installEnterprise;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getAnalyzerNumber() {
        return analyzerNumber;
    }

    public void setAnalyzerNumber(String analyzerNumber) {
        this.analyzerNumber = analyzerNumber;
    }

    public String getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public boolean getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(boolean goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsStatusName() {
        return goodsStatusName;
    }

    public void setGoodsStatusName(String goodsStatusName) {
        this.goodsStatusName = goodsStatusName;
    }

    public String getGoodsMemo() {
        return goodsMemo;
    }

    public void setGoodsMemo(String goodsMemo) {
        this.goodsMemo = goodsMemo;
    }

    public String getReturnMemo() {
        return returnMemo;
    }

    public void setReturnMemo(String returnMemo) {
        this.returnMemo = returnMemo;
    }

    public boolean isGoodsStatus() {
        return goodsStatus;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPurchaseContract() {
        return purchaseContract;
    }

    public void setPurchaseContract(String purchaseContract) {
        this.purchaseContract = purchaseContract;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    public String getGoodsSupplier() {
        return goodsSupplier;
    }

    public void setGoodsSupplier(String goodsSupplier) {
        this.goodsSupplier = goodsSupplier;
    }

    public String getDeliverPoint() {
        return deliverPoint;
    }

    public void setDeliverPoint(String deliverPoint) {
        this.deliverPoint = deliverPoint;
    }

    public String getWarrantyClause() {
        return warrantyClause;
    }

    public void setWarrantyClause(String warrantyClause) {
        this.warrantyClause = warrantyClause;
    }
    
}
