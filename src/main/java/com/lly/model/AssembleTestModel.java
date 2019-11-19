package com.lly.model;

/**
 * @Author: WangLei
 * @Description: 组装测试模板类
 * @Date: Create in 2019/3/25 14:18
 * @Modify by WangLei
 */
public class AssembleTestModel extends BaseModel {

    private String testId;
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
    private String beginTime;
    private String endTime;
    private String testMemo;
    private String partsModelString;
    private Boolean finishFlag;
    private String optType;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
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

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTestMemo() {
        return testMemo;
    }

    public void setTestMemo(String testMemo) {
        this.testMemo = testMemo;
    }

    public String getPartsModelString() {
        return partsModelString;
    }

    public void setPartsModelString(String partsModelString) {
        this.partsModelString = partsModelString;
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
