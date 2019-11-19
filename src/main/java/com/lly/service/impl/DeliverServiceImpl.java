package com.lly.service.impl;

import com.lly.dao.IAssembleDao;
import com.lly.dao.IDeliverDao;
import com.lly.dao.IDeliverGoodsDao;
import com.lly.pojo.AssemblePojo;
import com.lly.pojo.DeliverGoodsPojo;
import com.lly.pojo.DeliverPojo;
import com.lly.service.IDeliverService;
import com.lly.util.DefaultParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("deliverService")
@Transactional(rollbackFor = Exception.class)
public class DeliverServiceImpl implements IDeliverService {

    @Resource
    private IDeliverDao deliverDao;

    @Resource
    private IDeliverGoodsDao deliverGoodsDao;

    @Resource
    private IAssembleDao assembleDao;

    @Override
    public int getDeliverCount(DeliverPojo deliverPojo, List<String> statusCodeList) {
        return deliverDao.getDeliverCount(deliverPojo, statusCodeList);
    }

    @Override
    public List<DeliverPojo> getDeliver(DeliverPojo deliverPojo, List<String> statusCodeList) {
        return deliverDao.getDeliver(deliverPojo, statusCodeList);
    }

    @Override
    public int insertDeliver(DeliverPojo deliverPojo, List<DeliverGoodsPojo> deliverGoodsPojoList) throws Exception {
        int result = 0;
        result = deliverDao.insertDeliver(deliverPojo);
        if (result > 0) {
            if (deliverGoodsPojoList != null && deliverGoodsPojoList.size() > 0) {
                for (DeliverGoodsPojo temp : deliverGoodsPojoList) {
                    DeliverPojo deliverPojoTemp = new DeliverPojo();
                    deliverPojoTemp.setDeliverId(deliverPojo.getDeliverId());
                    temp.setDeliverPojo(deliverPojoTemp);
                }
                deliverGoodsDao.insertDeliverGoods(deliverGoodsPojoList);
                for (DeliverGoodsPojo deliverGoodsPojo : deliverGoodsPojoList) {
                    AssemblePojo assemblePojo = deliverGoodsPojo.getAssemblePojo();
                    if (assemblePojo != null && assemblePojo.getAssembleId() != null && assemblePojo.getAssembleId() > 0) {
                        assembleDao.updateAssembleStatus(String.valueOf(assemblePojo.getAssembleId()), DefaultParam.PRODUCT_DELIVER);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int updateDeliver(DeliverPojo deliverPojo, List<DeliverGoodsPojo> deliverGoodsPojoList, boolean updateGoodsFlag) throws Exception {
        int result = 0;
        result = deliverDao.updateDeliver(deliverPojo);
        if (result > 0) {
            if (updateGoodsFlag) {
                if (deliverGoodsPojoList != null && deliverGoodsPojoList.size() > 0) {
                    for (DeliverGoodsPojo temp : deliverGoodsPojoList) {
                        DeliverPojo deliverPojoTemp = new DeliverPojo();
                        deliverPojoTemp.setDeliverId(deliverPojo.getDeliverId());
                        temp.setDeliverPojo(deliverPojoTemp);
                    }
                    deliverGoodsDao.updateDeliverGoods(deliverGoodsPojoList);
                    for (DeliverGoodsPojo deliverGoodsPojo : deliverGoodsPojoList) {
                        AssemblePojo assemblePojo = deliverGoodsPojo.getAssemblePojo();
                        if (assemblePojo != null && assemblePojo.getAssembleId() > 0) {
                            assembleDao.updateAssembleStatus(String.valueOf(assemblePojo.getAssembleId()), DefaultParam.PRODUCT_DELIVER);
                        }
                    }
                }
            } else {
                List<String> idList = new ArrayList<>();
                idList.add(String.valueOf(deliverPojo.getDeliverId()));
                assembleDao.updateAssembleStatusBatchByDeliver(idList, DefaultParam.PRODUCT_DELIVER_RESET);
                deliverGoodsDao.deleteDeliverGoods(String.valueOf(deliverPojo.getDeliverId()), null);
                if (deliverGoodsPojoList != null && deliverGoodsPojoList.size() > 0) {
                    for (DeliverGoodsPojo temp : deliverGoodsPojoList) {
                        DeliverPojo deliverPojoTemp = new DeliverPojo();
                        deliverPojoTemp.setDeliverId(deliverPojo.getDeliverId());
                        temp.setDeliverPojo(deliverPojoTemp);
                    }
                    deliverGoodsDao.insertDeliverGoods(deliverGoodsPojoList);
                    for (DeliverGoodsPojo deliverGoodsPojo : deliverGoodsPojoList) {
                        AssemblePojo assemblePojo = deliverGoodsPojo.getAssemblePojo();
                        if (assemblePojo != null && assemblePojo.getAssembleId() != null) {
                            assembleDao.updateAssembleStatus(String.valueOf(assemblePojo.getAssembleId()), DefaultParam.PRODUCT_DELIVER);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int deleteDelivers(List<String> idList) throws Exception {
        int result = assembleDao.updateAssembleStatusBatchByDeliver(idList, DefaultParam.PRODUCT_DELIVER_RESET);
        if (result >= 0) {
            result = deliverDao.deleteDelivers(idList);
        }
        return result;
    }

    @Override
    public boolean canDeliverDelete(String deliverId) throws Exception {
        boolean canDelete = false;
        if (deliverDao.canDeliverDelete(deliverId) > 0) {
            canDelete = true;
        } else {
            canDelete = false;
        }
        return canDelete;
    }

    @Override
    public boolean canDeliverModify(String deliverId) throws Exception {
        boolean canModify = false;
        if (deliverDao.canDeliverModify(deliverId) > 0) {
            canModify = true;
        } else {
            canModify = false;
        }
        return canModify;
    }

}
