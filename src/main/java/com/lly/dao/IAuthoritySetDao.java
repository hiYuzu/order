package com.lly.dao;

import com.lly.pojo.AuthoritySetPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 权限设置数据库映射接口
 * @Date: Create in 2018/3/8 14:45
 * @Modify by WangLei
 */
public interface IAuthoritySetDao {

    /**
     * 是否存在页面权限
     * @param authoritySetPojo
     * @return
     */
    int haveAuthority(@Param("authoritySetPojo")AuthoritySetPojo authoritySetPojo);

    /**
     * 新增权限设置信息
     * @param authoritySetPojoList
     * @return
     */
    int insertAuthoritySet(@Param("authoritySetPojoList")List<AuthoritySetPojo> authoritySetPojoList);

    /**
     * 更新权限设置信息
     * @param authoritySetPojo
     * @return
     */
    int updateAuthoritySet(@Param("authoritySetPojo")AuthoritySetPojo authoritySetPojo);

    /**
     * 删除权限设置信息
     * @param userType
     * @return
     */
    int deleteAuthoritySet(@Param("userType")String userType);

}
