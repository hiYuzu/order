package com.lly.dao;

import com.lly.pojo.DeliverStatusPojo;

import java.util.List;

public interface IDeliverStatusDao {

    /**
     * 获取发货单状态信息
     *
     * @return
     */
    List<DeliverStatusPojo> getDeliverStatus();

}
