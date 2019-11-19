package com.tcb.pojo;

/**
 * @Author: WangLei
 * @Description: 组装部件POJO
 * @Date: Create in 2019/3/18 10:25
 * @Modify by WangLei
 */
public class AssemblePartPojo extends BasePojo {

    private Long partId;
    private AssemblePojo assemblePojo;
    private ParamPojo partType;
    private String partNo;
    private String partMemo;

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public AssemblePojo getAssemblePojo() {
        return assemblePojo;
    }

    public void setAssemblePojo(AssemblePojo assemblePojo) {
        this.assemblePojo = assemblePojo;
    }

    public ParamPojo getPartType() {
        return partType;
    }

    public void setPartType(ParamPojo partType) {
        this.partType = partType;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getPartMemo() {
        return partMemo;
    }

    public void setPartMemo(String partMemo) {
        this.partMemo = partMemo;
    }

}
