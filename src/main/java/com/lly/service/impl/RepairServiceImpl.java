package com.lly.service.impl;

import com.lly.dao.IRepairDao;
import com.lly.pojo.GoodsRepairPojo;
import com.lly.service.IRepairService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("repairService")
@Transactional(rollbackFor = Exception.class)
public class RepairServiceImpl implements IRepairService {

    @Resource
    private IRepairDao repairDao;

    @Override
    public int getRepairGoodsCount(GoodsRepairPojo goodsRepairPojo) {
        return repairDao.getRepairGoodsCount(goodsRepairPojo);
    }

    @Override
    public List<GoodsRepairPojo> getRepairGoods(GoodsRepairPojo goodsRepairPojo) {
        return repairDao.getRepairGoods(goodsRepairPojo);
    }

    @Override
    public int insertRepairGoods(GoodsRepairPojo goodsRepairPojo) throws Exception {
        return repairDao.insertRepairGoods(goodsRepairPojo);
    }

    @Override
    public int updateRepairGoods(GoodsRepairPojo goodsRepairPojo) throws Exception {
        return repairDao.updateRepairGoods(goodsRepairPojo);
    }

    @Override
    public int deleteRepairGoods(String repairId) throws Exception {
        return repairDao.deleteRepairGoods(repairId);
    }
}
