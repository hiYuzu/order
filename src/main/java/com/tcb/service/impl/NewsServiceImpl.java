package com.tcb.service.impl;

import com.tcb.dao.INewsDao;
import com.tcb.pojo.NewsPojo;
import com.tcb.service.INewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 公告服务接口实现类
 * @Date: Create in 2018/3/12 15:48
 * @Modify by WangLei
 */
@Service("newsService")
@Transactional(rollbackFor = Exception.class)
public class NewsServiceImpl implements INewsService {

    @Resource
    private INewsDao newsDao;

    @Override
    public int getNewsCount(NewsPojo newsPojo) {
        return newsDao.getNewsCount(newsPojo);
    }

    @Override
    public List<NewsPojo> getNews(NewsPojo newsPojo) {
        return newsDao.getNews(newsPojo);
    }

    @Override
    public int insertNews(NewsPojo newsPojo) throws Exception {
        return newsDao.insertNews(newsPojo);
    }

    @Override
    public int updateNews(NewsPojo newsPojo) throws Exception {
        return newsDao.updateNews(newsPojo);
    }

    @Override
    public int deleteNews(List<String> idList) throws Exception {
        return newsDao.deleteNews(idList);
    }
}
