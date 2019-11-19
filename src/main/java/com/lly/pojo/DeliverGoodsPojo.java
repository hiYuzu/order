package com.lly.pojo;

public class DeliverGoodsPojo extends BasePojo {

    private long goodsId;
    private DeliverPojo deliverPojo;
    private AssemblePojo assemblePojo;
    private ParamPojo paramPojo;
    private String snCode;
    private String installEnterprise;
    private String jobNo;
    private String analyzerNumber;
    private String goodsAmount;
    private boolean goodsStatus;
    private String goodsMemo;
    private String returnMemo;
    //外购相关
    private String goodsName;
    private String purchaseContract;
    private String storageDate;
    private String goodsSupplier;
    private String deliverPoint;
    private String warrantyClause;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public DeliverPojo getDeliverPojo() {
        return deliverPojo;
    }

    public void setDeliverPojo(DeliverPojo deliverPojo) {
        this.deliverPojo = deliverPojo;
    }

    public AssemblePojo getAssemblePojo() {
        return assemblePojo;
    }

    public void setAssemblePojo(AssemblePojo assemblePojo) {
        this.assemblePojo = assemblePojo;
    }

    public ParamPojo getParamPojo() {
        return paramPojo;
    }

    public void setParamPojo(ParamPojo paramPojo) {
        this.paramPojo = paramPojo;
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
