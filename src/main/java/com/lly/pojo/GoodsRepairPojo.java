package com.lly.pojo;

import java.sql.Timestamp;

public class GoodsRepairPojo extends BasePojo {

    private long repairId;
    private GoodsInstallPojo goodsInstallPojo;
    private String repairUser;
    private Timestamp repairTime;
    private String materielName;
    private String materielNumber;

    public long getRepairId() {
        return repairId;
    }

    public void setRepairId(long repairId) {
        this.repairId = repairId;
    }

    public GoodsInstallPojo getGoodsInstallPojo() {
        return goodsInstallPojo;
    }

    public void setGoodsInstallPojo(GoodsInstallPojo goodsInstallPojo) {
        this.goodsInstallPojo = goodsInstallPojo;
    }

    public String getRepairUser() {
        return repairUser;
    }

    public void setRepairUser(String repairUser) {
        this.repairUser = repairUser;
    }

    public Timestamp getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Timestamp repairTime) {
        this.repairTime = repairTime;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public String getMaterielNumber() {
        return materielNumber;
    }

    public void setMaterielNumber(String materielNumber) {
        this.materielNumber = materielNumber;
    }

}
