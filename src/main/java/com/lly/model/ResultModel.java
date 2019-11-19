package com.lly.model;

/**
 * @Author: WangLei
 * @Description: 返回结果模板类
 * @Date: Create in 2018/1/25 13:29
 * @Modify by WangLei
 */
public class ResultModel {

    /**
     * 是否成功标志
     */
    private boolean result = false;

    /**
     * 详细信息
     */
    private String detail = null;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
