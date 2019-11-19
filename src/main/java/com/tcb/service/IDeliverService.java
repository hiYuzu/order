package com.tcb.service;

import com.tcb.pojo.DeliverGoodsPojo;
import com.tcb.pojo.DeliverPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单服务接口
 * @Date: Create in 2018/2/11 10:07
 * @Modify by WangLei
 */
public interface IDeliverService {

    /**
     * 查询发货单数据个数
     *
     * @param deliverPojo
     * @param statusCodeList
     * @return
     */
    int getDeliverCount(DeliverPojo deliverPojo,List<String> statusCodeList);

    /**
     * 查询发货单数据
     *
     * @param deliverPojo
     * @param statusCodeList
     * @return
     */
    List<DeliverPojo> getDeliver(DeliverPojo deliverPojo,List<String> statusCodeList);

    /**
     * 新增发货单
     *
     * @param deliverPojo
     * @param deliverGoodsPojoList
     * @return
     */
    int insertDeliver(DeliverPojo deliverPojo, List<DeliverGoodsPojo> deliverGoodsPojoList) throws Exception;

    /**
     * 更新发货单
     *
     * @param deliverPojo
     * @param deliverGoodsPojoList
     * @param updateGoodsFlag
     * @return
     */
    int updateDeliver(DeliverPojo deliverPojo, List<DeliverGoodsPojo> deliverGoodsPojoList,boolean updateGoodsFlag) throws Exception;

    /**
     * 删除发货单
     *
     * @param idList
     * @return
     */
    int deleteDelivers(List<String> idList) throws Exception;

    /**
     *  发货单是否能被删除
      * @param deliverId
     * @return
     * @throws Exception
     */
    boolean canDeliverDelete(String deliverId) throws Exception;

    /**
     * 发货单是否能被修改
     * @param deliverId
     * @return
     * @throws Exception
     */
    boolean canDeliverModify(String deliverId) throws Exception;

}
