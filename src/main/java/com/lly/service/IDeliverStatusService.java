package com.lly.service;

import com.lly.pojo.DeliverStatusPojo;

import java.util.List;

public interface IDeliverStatusService {

    /**
     * 获取发货单状态信息
     *
     * @return
     */
    List<DeliverStatusPojo> getDeliverStatus();

}
