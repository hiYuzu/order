package com.lly.service.impl;

import com.lly.dao.IDeliverStatusDao;
import com.lly.pojo.DeliverStatusPojo;
import com.lly.service.IDeliverStatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("deliverStatusService")
public class DeliverStatusServiceImpl implements IDeliverStatusService {

    @Resource
    private IDeliverStatusDao deliverStatusDao;

    @Override
    public List<DeliverStatusPojo> getDeliverStatus() {
        return deliverStatusDao.getDeliverStatus();
    }
}
