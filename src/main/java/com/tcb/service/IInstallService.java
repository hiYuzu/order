package com.tcb.service;

import com.tcb.pojo.GoodsInstallPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 货物安装服务接口
 * @Date: Create in 2018/2/27 13:48
 * @Modify by WangLei
 */
public interface IInstallService {

    /**
     * 查询安装货物个数
     * @param goodsInstallPojo
     * @param deliverStatusCodeList
     * @return
     */
    int getInstallGoodsCount(GoodsInstallPojo goodsInstallPojo,List<String> deliverStatusCodeList);

    /**
     * 查询安装货物信息
     * @param goodsInstallPojo
     * @param deliverStatusCodeList
     * @return
     */
    List<GoodsInstallPojo> getInstallGoods(GoodsInstallPojo goodsInstallPojo,List<String> deliverStatusCodeList);

    /**
     * 处理安装货物信息
     * @param goodsInstallPojo
     * @return
     */
    int operateInstallGoods(GoodsInstallPojo goodsInstallPojo) throws Exception;

    /**
     * 是否存在安装货物
     * @param goodsId
     * @return
     */
    int existInstallGoods(String goodsId);

    /**
     * 是否发货单全部安装完成
     * @param deliverId
     * @return
     */
    int existNotInstallGoods(String deliverId);
}
