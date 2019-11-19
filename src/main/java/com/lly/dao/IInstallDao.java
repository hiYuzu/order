package com.lly.dao;

import com.lly.pojo.GoodsInstallPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInstallDao {

    /**
     * 查询安装货物个数
     * @param goodsInstallPojo
     * @param deliverStatusCodeList
     * @return
     */
    int getInstallGoodsCount(@Param("goodsInstallPojo")GoodsInstallPojo goodsInstallPojo,@Param("deliverStatusCodeList")List<String> deliverStatusCodeList);

    /**
     * 查询安装货物信息
     * @param goodsInstallPojo
     * @param deliverStatusCodeList
     * @return
     */
    List<GoodsInstallPojo> getInstallGoods(@Param("goodsInstallPojo")GoodsInstallPojo goodsInstallPojo,@Param("deliverStatusCodeList")List<String> deliverStatusCodeList);

    /**
     * 新增安装货物信息
     * @param goodsInstallPojo
     * @return
     */
    int insertInstallGoods(@Param("goodsInstallPojo")GoodsInstallPojo goodsInstallPojo);

    /**
     * 更新安装货物信息
     * @param goodsInstallPojo
     * @return
     */
    int updateInstallGoods(@Param("goodsInstallPojo")GoodsInstallPojo goodsInstallPojo);

    /**
     * 是否存在安装货物
     * @param goodsId
     * @return
     */
    int existInstallGoods(@Param("goodsId") String goodsId);

    /**
     * 是否发货单全部安装完成
     * @param deliverId
     * @return
     */
    int existNotInstalledGoods(@Param("deliverId") String deliverId);

    /**
     * 是否发货单存在安装完成
     * @param deliverId
     * @return
     */
    int existInstalledGoods(@Param("deliverId") String deliverId);

}
