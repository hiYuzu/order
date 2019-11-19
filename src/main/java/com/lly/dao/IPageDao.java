package com.lly.dao;

import com.lly.pojo.PagePojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 系统页面数据库映射类
 * @Date: Create in 2018/3/8 11:35
 * @Modify by WangLei
 */
public interface IPageDao {

    /**
     * 获取系统页面
     * @param pagePojo
     * @return
     */
    List<PagePojo> getPage(@Param("pagePojo") PagePojo pagePojo);


}
