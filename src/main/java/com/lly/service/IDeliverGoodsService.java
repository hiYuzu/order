package com.lly.service;

import com.lly.pojo.DeliverGoodsPojo;

import java.util.List;

public interface IDeliverGoodsService {

    /**
     * 查询发货单货物个数
     *
     * @param deliverGoodsPojo
     * @return
     */
    int getDeliverGoodsCount(DeliverGoodsPojo deliverGoodsPojo);

    /**
     * 查询发货单货物
     *
     * @param deliverGoodsPojo
     * @return
     */
    List<DeliverGoodsPojo> getDeliverGoods(DeliverGoodsPojo deliverGoodsPojo);

    /**
     * 查询发货单货物(根据发货单Id)
     *
     * @param deliverId
     * @return
     */
    List<DeliverGoodsPojo> getDeliverGoodsByDeliverId(String deliverId);

    /**
     * 更新发货单货物状态
     *
     * @param deliverGoodsPojo
     * @return
     */
    int updateDeliverGoodsStatus(DeliverGoodsPojo deliverGoodsPojo) throws Exception;

    /**
     * 更新发货单货物备注
     * @param deliverGoodsPojo
     * @return
     * @throws Exception
     */
    int updateDeliverGoodsMemo(DeliverGoodsPojo deliverGoodsPojo) throws Exception;

}
