package com.lly.service;

import com.lly.pojo.DeliverGoodsPojo;
import com.lly.pojo.DeliverPojo;

import java.util.List;

public interface IBusinessService {

    /**
     * 发货单商务处理
     * @param deliverPojo
     * @param deliverGoodsPojoList
     * @return
     */
    int businessDeliver(DeliverPojo deliverPojo,List<DeliverGoodsPojo> deliverGoodsPojoList) throws Exception;

    /**
     * 是否发货单能被商务处理
     * @param deliverId
     * @return
     */
    boolean canDeliverBusiness(String deliverId);

    /**
     * 更新技术合同相关信息
     * @param deliverPojo
     * @return
     */
    int updateTsContract(DeliverPojo deliverPojo) throws Exception;

}
