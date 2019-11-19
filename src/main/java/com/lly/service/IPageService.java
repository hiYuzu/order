package com.lly.service;

import com.lly.pojo.PagePojo;

import java.util.List;

public interface IPageService {

    /**
     * 获取系统页面
     * @param pagePojo
     * @return
     */
    List<PagePojo> getPage(PagePojo pagePojo);

}
