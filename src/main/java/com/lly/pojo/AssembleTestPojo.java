package com.lly.pojo;

import java.sql.Timestamp;

public class AssembleTestPojo extends BasePojo {

    private Long testId;
    private AssemblePojo assemblePojo;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Boolean passFlag;
    private String testMemo;

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public AssemblePojo getAssemblePojo() {
        return assemblePojo;
    }

    public void setAssemblePojo(AssemblePojo assemblePojo) {
        this.assemblePojo = assemblePojo;
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

    public Boolean getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(Boolean passFlag) {
        this.passFlag = passFlag;
    }

    public String getTestMemo() {
        return testMemo;
    }

    public void setTestMemo(String testMemo) {
        this.testMemo = testMemo;
    }

}
