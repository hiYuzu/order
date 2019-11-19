package com.lly.dao;

import com.lly.pojo.NewsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 公告数据库映射类
 * @Date: Create in 2018/3/12 15:25
 * @Modify by WangLei
 */
public interface INewsDao {

    /**
     * 查询公告个数
     * @param newsPojo
     * @return
     */
    int getNewsCount(@Param("newsPojo")NewsPojo newsPojo);

    /**
     * 查询公告
     * @param newsPojo
     * @return
     */
    List<NewsPojo> getNews(@Param("newsPojo")NewsPojo newsPojo);

    /**
     * 新增公告
     * @param newsPojo
     * @return
     */
    int insertNews(@Param("newsPojo")NewsPojo newsPojo);

    /**
     * 更新公告
     * @param newsPojo
     * @return
     */
    int updateNews(@Param("newsPojo")NewsPojo newsPojo);

    /**
     * 删除公告
     * @param idList
     * @return
     */
    int deleteNews(@Param("idList")List<String> idList);

}
