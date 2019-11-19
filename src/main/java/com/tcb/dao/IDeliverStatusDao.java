package com.tcb.dao;

import com.tcb.pojo.DeliverStatusPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单状态数据库接口
 * @Date: Create in 2018/1/24 10:57
 * @Modify by WangLei
 */
public interface IDeliverStatusDao {

    /**
     * 获取发货单状态信息
     *
     * @return
     */
    List<DeliverStatusPojo> getDeliverStatus();

}
