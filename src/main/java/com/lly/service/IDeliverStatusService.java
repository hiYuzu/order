package com.lly.service;

import com.lly.pojo.DeliverStatusPojo;

import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单状态服务接口
 * @Date: Create in 2018/2/11 10:07
 * @Modify by WangLei
 */
public interface IDeliverStatusService {

    /**
     * 获取发货单状态信息
     *
     * @return
     */
    List<DeliverStatusPojo> getDeliverStatus();

}
