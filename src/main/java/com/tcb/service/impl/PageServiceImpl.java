package com.tcb.service.impl;

import com.tcb.dao.IPageDao;
import com.tcb.pojo.PagePojo;
import com.tcb.service.IPageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 系统页面服务接口实现类
 * @Date: Create in 2018/3/8 11:52
 * @Modify by WangLei
 */
@Service("pageService")
@Transactional(rollbackFor = Exception.class)
public class PageServiceImpl implements IPageService {

    @Resource
    private IPageDao pageDao;

    @Override
    public List<PagePojo> getPage(PagePojo pagePojo) {
        return pageDao.getPage(pagePojo);
    }
}
