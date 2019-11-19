package com.tcb.model;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 组装模板类
 * @Date: Create in 2019/3/19 9:19
 * @Modify by WangLei
 */
public class AssembleModel extends BaseModel {

    private String assembleId;
    private String pnCode;
    private String pnName;
    private String snCode;
    private String assembleStatusCode;
    private String assembleStatusName;
    private String jobNo;
    private String cruxNo;
    private String assembleMemo;
    private String completeDate;
    private List<AssemblePartModel> assemblePartModelList;
    private String partsModelString;
    private Boolean finishFlag;
    private String optType;

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

    public String getAssembleStatusCode() {
        return assembleStatusCode;
    }

    public void setAssembleStatusCode(String assembleStatusCode) {
        this.assembleStatusCode = assembleStatusCode;
    }

    public String getAssembleStatusName() {
        return assembleStatusName;
    }

    public void setAssembleStatusName(String assembleStatusName) {
        this.assembleStatusName = assembleStatusName;
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

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getPartsModelString() {
        return partsModelString;
    }

    public void setPartsModelString(String partsModelString) {
        this.partsModelString = partsModelString;
    }

    public List<AssemblePartModel> getAssemblePartModelList() {
        return assemblePartModelList;
    }

    public void setAssemblePartModelList(List<AssemblePartModel> assemblePartModelList) {
        this.assemblePartModelList = assemblePartModelList;
    }

    public Boolean getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(Boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

}
