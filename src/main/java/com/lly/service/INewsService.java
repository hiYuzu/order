package com.lly.service;

import com.lly.pojo.NewsPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 公告服务接口
 * @Date: Create in 2018/3/12 15:47
 * @Modify by Lenovo
 */
public interface INewsService {

    /**
     * 查询公告个数
     * @param newsPojo
     * @return
     */
    int getNewsCount(NewsPojo newsPojo);

    /**
     * 查询公告
     * @param newsPojo
     * @return
     */
    List<NewsPojo> getNews(NewsPojo newsPojo);

    /**
     * 新增公告
     * @param newsPojo
     * @return
     */
    int insertNews(NewsPojo newsPojo) throws Exception;

    /**
     * 更新公告
     * @param newsPojo
     * @return
     */
    int updateNews(NewsPojo newsPojo) throws Exception;

    /**
     * 删除公告
     * @param idList
     * @return
     */
    int deleteNews(List<String> idList) throws Exception;

}
