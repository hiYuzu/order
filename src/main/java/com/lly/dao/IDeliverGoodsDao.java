package com.lly.dao;

import com.lly.pojo.DeliverGoodsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IDeliverGoodsDao {

    /**
     * 查询发货单货物个数
     *
     * @param deliverGoodsPojo
     * @return
     */
    int getDeliverGoodsCount(@Param("deliverGoodsPojo") DeliverGoodsPojo deliverGoodsPojo);

    /**
     * 查询发货单货物
     *
     * @param deliverGoodsPojo
     * @return
     */
    List<DeliverGoodsPojo> getDeliverGoods(@Param("deliverGoodsPojo") DeliverGoodsPojo deliverGoodsPojo);

    /**
     * 查询发货单货物(根据发货单Id)
     *
     * @param deliverId
     * @return
     */
    List<DeliverGoodsPojo> getDeliverGoodsByDeliverId(@Param("deliverId") String deliverId);

    /**
     * 新增发货单货物
     *
     * @param deliverGoodsPojoList
     * @return
     */
    int insertDeliverGoods(@Param("deliverGoodsPojoList") List<DeliverGoodsPojo> deliverGoodsPojoList);

    /**
     * 更新发货单货物
     *
     * @param deliverGoodsPojoList
     * @return
     */
    int updateDeliverGoods(@Param("deliverGoodsPojoList") List<DeliverGoodsPojo> deliverGoodsPojoList);

    /**
     * 更新发货单货物状态
     *
     * @param deliverGoodsPojo
     * @return
     */
    int updateDeliverGoodsStatus(@Param("deliverGoodsPojo") DeliverGoodsPojo deliverGoodsPojo);

    /**
     * 更新发货单货物备注
     *
     * @param deliverGoodsPojo
     * @return
     */
    int updateDeliverGoodsMemo(@Param("deliverGoodsPojo") DeliverGoodsPojo deliverGoodsPojo);

    /**
     * 更新发货单货安装厂家
     *
     * @param deliverGoodsPojo
     * @return
     */
    int updateDeliverGoodsInstallEnterprise(@Param("deliverGoodsPojo") DeliverGoodsPojo deliverGoodsPojo);

    /**
     * 删除发货单货物
     *
     * @param deliverId
     * @param idList
     * @return
     */
    int deleteDeliverGoods(@Param("deliverId") String deliverId, @Param("idList") List<String> idList);

}
