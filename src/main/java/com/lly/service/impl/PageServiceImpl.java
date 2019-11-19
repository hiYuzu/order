package com.lly.service.impl;

import com.lly.dao.IPageDao;
import com.lly.pojo.PagePojo;
import com.lly.service.IPageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
