package com.lly.pojo;

import java.sql.Timestamp;

/**
 * @Author: WangLei
 * @Description: 组装老化POJO
 * @Date: Create in 2019/3/25 10:35
 * @Modify by WangLei
 */
public class AssembleOldPojo extends BasePojo {

    private Long oldId;
    private AssemblePojo assemblePojo;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Boolean passFlag;
    private String oldMemo;

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
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

    public String getOldMemo() {
        return oldMemo;
    }

    public void setOldMemo(String oldMemo) {
        this.oldMemo = oldMemo;
    }

}
