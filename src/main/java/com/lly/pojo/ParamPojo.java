package com.lly.pojo;

/**
 * @Author: WangLei
 * @Description: 系统参数POJO
 * @Date: Create in 2018/1/24 10:35
 * @Modify by WangLei
 */
public class ParamPojo extends BasePojo {

    private int paramId;
    private ParamType paramType;
    private String paramCode;
    private String paramName;

    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    public ParamType getParamType() {
        return paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

}
