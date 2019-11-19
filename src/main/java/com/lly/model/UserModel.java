package com.lly.model;

/**
 * @Author: WangLei
 * @Description: 用户模板类
 * @Date: Create in 2018/1/25 13:38
 * @Modify by WangLei
 */
public class UserModel extends BaseModel{

    private String userId;
    private String userCode;
    private String userPassword;
    private String userName;
    private String userType;
    private String userTypeName;
    private boolean stopFlag;
    private String stopFlagName;
    private String endTime;
    private String userDetail;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public boolean getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getStopFlagName() {
        return stopFlagName;
    }

    public void setStopFlagName(String stopFlagName) {
        this.stopFlagName = stopFlagName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(String userDetail) {
        this.userDetail = userDetail;
    }

}
