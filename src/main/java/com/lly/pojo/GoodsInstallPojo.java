package com.lly.pojo;

import java.sql.Timestamp;

public class GoodsInstallPojo extends BasePojo {

    private long installId;
    private DeliverGoodsPojo deliverGoodsPojo;
    private String installUser;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Timestamp warrantyPeriod;
    private Boolean installStatus;
    private String contractNewCode;
    private Timestamp deliverDate;
    private Timestamp contractBeginTime;
    private Timestamp contractEndTime;
    private String instrumentBrand;
    private String installMemo;

    public long getInstallId() {
        return installId;
    }

    public void setInstallId(long installId) {
        this.installId = installId;
    }

    public DeliverGoodsPojo getDeliverGoodsPojo() {
        return deliverGoodsPojo;
    }

    public void setDeliverGoodsPojo(DeliverGoodsPojo deliverGoodsPojo) {
        this.deliverGoodsPojo = deliverGoodsPojo;
    }

    public String getInstallUser() {
        return installUser;
    }

    public void setInstallUser(String installUser) {
        this.installUser = installUser;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Timestamp warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Boolean getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(Boolean installStatus) {
        this.installStatus = installStatus;
    }

    public String getContractNewCode() {
        return contractNewCode;
    }

    public void setContractNewCode(String contractNewCode) {
        this.contractNewCode = contractNewCode;
    }

    public Timestamp getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Timestamp deliverDate) {
        this.deliverDate = deliverDate;
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

    public String getInstrumentBrand() {
        return instrumentBrand;
    }

    public void setInstrumentBrand(String instrumentBrand) {
        this.instrumentBrand = instrumentBrand;
    }

    public String getInstallMemo() {
        return installMemo;
    }

    public void setInstallMemo(String installMemo) {
        this.installMemo = installMemo;
    }

}
