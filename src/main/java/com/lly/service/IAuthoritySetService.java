package com.lly.service;

import com.lly.pojo.AuthoritySetPojo;

import java.util.List;
import java.util.Map;

public interface IAuthoritySetService {

    /**
     * 是否存在页面权限
     * @param userType
     * @param pageCode
     * @return
     */
    int haveAuthority(String userType,String pageCode);

    /**
     * 处理权限设置信息
     * @param authoritySetPojoMap
     * @return
     */
    int operateAuthoritySet(Map<String,List<AuthoritySetPojo>> authoritySetPojoMap) throws Exception;

}
