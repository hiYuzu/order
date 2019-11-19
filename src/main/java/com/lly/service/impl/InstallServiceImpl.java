package com.lly.service.impl;

import com.lly.dao.IDeliverDao;
import com.lly.dao.IDeliverGoodsDao;
import com.lly.dao.IInstallDao;
import com.lly.pojo.GoodsInstallPojo;
import com.lly.service.IInstallService;
import com.lly.util.DefaultParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 货物安装服务接口实现类
 * @Date: Create in 2018/2/27 13:49
 * @Modify by WangLei
 */
@Service("installService")
@Transactional(rollbackFor = Exception.class)
public class InstallServiceImpl implements IInstallService {

    @Resource
    private IInstallDao installDao;

    @Resource
    private IDeliverDao deliverDao;

    @Resource
    private IDeliverGoodsDao deliverGoodsDao;

    @Override
    public int getInstallGoodsCount(GoodsInstallPojo goodsInstallPojo, List<String> deliverStatusCodeList) {
        return installDao.getInstallGoodsCount(goodsInstallPojo, deliverStatusCodeList);
    }

    @Override
    public List<GoodsInstallPojo> getInstallGoods(GoodsInstallPojo goodsInstallPojo, List<String> deliverStatusCodeList) {
        return installDao.getInstallGoods(goodsInstallPojo, deliverStatusCodeList);
    }

    @Override
    public int operateInstallGoods(GoodsInstallPojo goodsInstallPojo) throws Exception {
        int result = 0;
        if (goodsInstallPojo != null) {
            if (goodsInstallPojo.getDeliverGoodsPojo() != null
                    && installDao.existInstallGoods(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getGoodsId())) > 0) {
                result = installDao.updateInstallGoods(goodsInstallPojo);
            } else {
                result = installDao.insertInstallGoods(goodsInstallPojo);
            }
            //更新货物单
            if (result > 0) {
                if (goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo() != null) {
                    String deliverType = String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getDeliverType());
                    if(DefaultParam.OUTSOURCING.equals(deliverType)){
                        deliverGoodsDao.updateDeliverGoodsInstallEnterprise(goodsInstallPojo.getDeliverGoodsPojo());
                    }else{
                        //发货单可能没有填写安装地址，所以需要在这里更新
                        deliverGoodsDao.updateDeliverGoodsInstallEnterprise(goodsInstallPojo.getDeliverGoodsPojo());
                    }
                }
            }
            //更新发货单状态
            if (result > 0) {
                String statusCode;
                if (goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo() != null
                        && installDao.existNotInstalledGoods(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getDeliverId())) == 0) {
                    statusCode = DefaultParam.DELIVER_INSTALLED;
                } else if (goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo() != null
                        && installDao.existInstalledGoods(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getDeliverId())) > 0) {
                    statusCode = DefaultParam.DELIVER_INSTALLING;
                } else {
                    statusCode = DefaultParam.DELIVER_BUSINESS;
                }
                deliverDao.updateDeliveryStatus(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getDeliverId()), statusCode);
            }
        }
        return result;
    }

    @Override
    public int existInstallGoods(String goodsId) {
        return installDao.existInstallGoods(goodsId);
    }

    @Override
    public int existNotInstallGoods(String deliverId) {
        return installDao.existNotInstalledGoods(deliverId);
    }
}
