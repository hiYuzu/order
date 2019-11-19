package com.tcb.pojo;

/**
 * @Author: WangLei
 * @Description: 权限设置POJO
 * @Date: Create in 2018/3/8 15:04
 * @Modify by WangLei
 */
public class AuthoritySetPojo extends BasePojo {

    private long setId;
    private String userType;
    private PagePojo pagePojo;
    private String authorityFlag;

    public long getSetId() {
        return setId;
    }

    public void setSetId(long setId) {
        this.setId = setId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public PagePojo getPagePojo() {
        return pagePojo;
    }

    public void setPagePojo(PagePojo pagePojo) {
        this.pagePojo = pagePojo;
    }

    public String getAuthorityFlag() {
        return authorityFlag;
    }

    public void setAuthorityFlag(String authorityFlag) {
        this.authorityFlag = authorityFlag;
    }

}
