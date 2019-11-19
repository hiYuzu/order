package com.lly.service.impl;

import com.lly.dao.IDeliverGoodsDao;
import com.lly.pojo.DeliverGoodsPojo;
import com.lly.service.IDeliverGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("deliverGoodsService")
@Transactional(rollbackFor = Exception.class)
public class DeliverGoodsServiceImpl implements IDeliverGoodsService {

    @Resource
    private IDeliverGoodsDao deliverGoodsDao;

    @Override
    public int getDeliverGoodsCount(DeliverGoodsPojo deliverGoodsPojo) {
        return deliverGoodsDao.getDeliverGoodsCount(deliverGoodsPojo);
    }

    @Override
    public List<DeliverGoodsPojo> getDeliverGoods(DeliverGoodsPojo deliverGoodsPojo) {
        return deliverGoodsDao.getDeliverGoods(deliverGoodsPojo);
    }

    @Override
    public List<DeliverGoodsPojo> getDeliverGoodsByDeliverId(String deliverId) {
        return deliverGoodsDao.getDeliverGoodsByDeliverId(deliverId);
    }

    @Override
    public int updateDeliverGoodsStatus(DeliverGoodsPojo deliverGoodsPojo) throws Exception {
        return deliverGoodsDao.updateDeliverGoodsStatus(deliverGoodsPojo);
    }

    @Override
    public int updateDeliverGoodsMemo(DeliverGoodsPojo deliverGoodsPojo) throws Exception {
        return deliverGoodsDao.updateDeliverGoodsMemo(deliverGoodsPojo);
    }


}
