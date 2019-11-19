package com.lly.service.impl;

import com.lly.dao.IDeliverStatusDao;
import com.lly.pojo.DeliverStatusPojo;
import com.lly.service.IDeliverStatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单状态服务接口实现类
 * @Date: Create in 2018/2/11 10:17
 * @Modify by WangLei
 */
@Service("deliverStatusService")
public class DeliverStatusServiceImpl implements IDeliverStatusService {

    @Resource
    private IDeliverStatusDao deliverStatusDao;

    @Override
    public List<DeliverStatusPojo> getDeliverStatus() {
        return deliverStatusDao.getDeliverStatus();
    }
}
