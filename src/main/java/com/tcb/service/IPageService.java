package com.tcb.service;

import com.tcb.pojo.PagePojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 系统页面服务接口
 * @Date: Create in 2018/3/8 11:51
 * @Modify by WangLei
 */
public interface IPageService {

    /**
     * 获取系统页面
     * @param pagePojo
     * @return
     */
    List<PagePojo> getPage(PagePojo pagePojo);

}
