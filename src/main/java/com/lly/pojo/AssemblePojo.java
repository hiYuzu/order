package com.lly.pojo;

import java.sql.Timestamp;

/**
 * @Author: WangLei
 * @Description: 组装POJO
 * @Date: Create in 2019/3/18 10:14
 * @Modify by WangLei
 */
public class AssemblePojo extends BasePojo {

    private Long assembleId;
    private ParamPojo pnCode;
    private String snCode;
    private DeliverStatusPojo assembleStatus;
    private String jobNo;
    private String cruxNo;
    private String assembleMemo;
    private Timestamp completeDate;

    public Long getAssembleId() {
        return assembleId;
    }

    public void setAssembleId(Long assembleId) {
        this.assembleId = assembleId;
    }

    public ParamPojo getPnCode() {
        return pnCode;
    }

    public void setPnCode(ParamPojo pnCode) {
        this.pnCode = pnCode;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public DeliverStatusPojo getAssembleStatus() {
        return assembleStatus;
    }

    public void setAssembleStatus(DeliverStatusPojo assembleStatus) {
        this.assembleStatus = assembleStatus;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getCruxNo() {
        return cruxNo;
    }

    public void setCruxNo(String cruxNo) {
        this.cruxNo = cruxNo;
    }

    public String getAssembleMemo() {
        return assembleMemo;
    }

    public void setAssembleMemo(String assembleMemo) {
        this.assembleMemo = assembleMemo;
    }

    public Timestamp getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Timestamp completeDate) {
        this.completeDate = completeDate;
    }

}
