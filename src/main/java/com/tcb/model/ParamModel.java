package com.tcb.model;

/**
 * @Author: WangLei
 * @Description: 系统参数模板类
 * @Date: Create in 2018/2/11 14:57
 * @Modify by WangLei
 */
public class ParamModel extends BaseModel {

    private String paramId;
    private String paramTypeCode;
    private String paramTypeName;
    private String paramCode;
    private String paramName;

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamTypeCode() {
        return paramTypeCode;
    }

    public void setParamTypeCode(String paramTypeCode) {
        this.paramTypeCode = paramTypeCode;
    }

    public String getParamTypeName() {
        return paramTypeName;
    }

    public void setParamTypeName(String paramTypeName) {
        this.paramTypeName = paramTypeName;
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
