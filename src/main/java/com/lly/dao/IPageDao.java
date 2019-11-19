package com.lly.dao;

import com.lly.pojo.PagePojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPageDao {

    /**
     * 获取系统页面
     * @param pagePojo
     * @return
     */
    List<PagePojo> getPage(@Param("pagePojo") PagePojo pagePojo);


}
