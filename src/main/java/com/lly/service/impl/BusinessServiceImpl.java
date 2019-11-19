package com.lly.service.impl;

import com.lly.dao.IBusinessDao;
import com.lly.dao.IDeliverGoodsDao;
import com.lly.pojo.DeliverGoodsPojo;
import com.lly.pojo.DeliverPojo;
import com.lly.service.IBusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("businessService")
@Transactional(rollbackFor = Exception.class)
public class BusinessServiceImpl implements IBusinessService {

    @Resource
    private IBusinessDao businessDao;

    @Resource
    private IDeliverGoodsDao deliverGoodsDao;

    @Override
    public int businessDeliver(DeliverPojo deliverPojo, List<DeliverGoodsPojo> deliverGoodsPojoList) throws Exception {
        int result = 0;
        result = businessDao.businessDeliver(deliverPojo);
        if(result > 0){
            if (deliverGoodsPojoList != null && deliverGoodsPojoList.size() > 0) {
                for (DeliverGoodsPojo temp : deliverGoodsPojoList) {
                    DeliverPojo deliverPojoTemp = new DeliverPojo();
                    deliverPojoTemp.setDeliverId(deliverPojo.getDeliverId());
                    temp.setDeliverPojo(deliverPojoTemp);
                }
                deliverGoodsDao.updateDeliverGoods(deliverGoodsPojoList);
            }
        }
        return result;
    }

    @Override
    public boolean canDeliverBusiness(String deliverId) {
        boolean canBusiness = false;
        if(businessDao.canDeliverBusiness(deliverId)>0){
            canBusiness = true;
        }else{
            canBusiness = false;
        }
        return canBusiness;
    }

    @Override
    public int updateTsContract(DeliverPojo deliverPojo) throws Exception {
        int result = 0;
        result = businessDao.updateTsContract(deliverPojo);
        if(result>0){
            businessDao.updateInstallWarranty(
                    String.valueOf(deliverPojo.getDeliverId()),
                    deliverPojo.getContractEndTime());
        }
        return result;
    }
}
