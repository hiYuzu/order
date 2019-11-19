package com.lly.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lly.model.*;
import com.lly.pojo.DeliverGoodsPojo;
import com.lly.pojo.DeliverPojo;
import com.lly.service.IBusinessService;
import com.lly.service.IDeliverService;
import com.lly.util.DefaultParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/BusinessController")
public class BusinessController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "BusinessController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(BusinessController.class);

    @Resource
    private IBusinessService businessService;

    @Resource
    private IDeliverService deliverService;

    @Resource
    private DeliverController deliverController;

    @Resource
    private DeliverGoodsController deliverGoodsController;

    /**
     * 获取发货单信息
     *
     * @param deliverMode
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getDeliver", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeliverModel> getDeliver(DeliverModel deliverMode, HttpSession httpSession) {
        ResultListModel<DeliverModel> resultListModel = new ResultListModel<DeliverModel>();
        List<DeliverModel> deliverModelList = new ArrayList<DeliverModel>();
        List<DeliverPojo> deliverPojoList;
        DeliverPojo deliverPojo = deliverController.convertDeliverPojo(deliverMode, httpSession);
        List<String> statusCodeList = new ArrayList<String>();
        if (deliverMode.getFinishFlag() != null) {
            if (deliverMode.getFinishFlag()) {
                statusCodeList.add(DefaultParam.DELIVER_BUSINESS);//已商务,待安装
                statusCodeList.add(DefaultParam.DELIVER_INSTALLING);//安装中
                statusCodeList.add(DefaultParam.DELIVER_INSTALLED);//已安装
            } else {
                statusCodeList.add(DefaultParam.DELIVER_SUBMIT);//已提交,待商务
            }
        } else {
            statusCodeList.add(DefaultParam.DELIVER_SUBMIT);//已提交,待商务
            statusCodeList.add(DefaultParam.DELIVER_BUSINESS);//已商务,待安装
            statusCodeList.add(DefaultParam.DELIVER_INSTALLING);//安装中
            statusCodeList.add(DefaultParam.DELIVER_INSTALLED);//已安装
        }
        int count = deliverService.getDeliverCount(deliverPojo, statusCodeList);
        if (count > 0) {
            deliverPojoList = deliverService.getDeliver(deliverPojo, statusCodeList);
            for (DeliverPojo temp : deliverPojoList) {
                DeliverModel deliverModelTemp = deliverController.convertDeliverModel(temp);
                if (deliverModelTemp != null) {
                    deliverModelList.add(deliverModelTemp);
                }
            }
            resultListModel.setData(deliverModelList);
        }
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 发货单商务处理
     *
     * @param deliverModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/businessDeliver", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel businessDeliver(DeliverModel deliverModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deliverModel != null) {
            try {
                if (deliverModel.getOptType() != null && deliverModel.getOptType().equals(DefaultParam.YES)) {
                    deliverModel.setDeliverStatusCode(DefaultParam.DELIVER_BUSINESS);
                    deliverModel.setDeliverStatusName("已商务，待安装");
                } else {
                    deliverModel.setDeliverStatusCode(DefaultParam.DELIVER_REJECT);
                    deliverModel.setDeliverStatusName("被拒绝，待修改");
                }
                if (!businessService.canDeliverBusiness(deliverModel.getDeliverId())) {
                    resultModel.setResult(false);
                    resultModel.setDetail("不能商务处理发货单！");
                    return resultModel;
                }
                DeliverPojo deliverPojo = deliverController.convertDeliverPojo(deliverModel, httpSession);
                List<DeliverGoodsPojo> deliverGoodsPojoList = new ArrayList<DeliverGoodsPojo>();
                if (deliverModel.getGoodsModelString() != null && !deliverModel.getGoodsModelString().isEmpty()) {
                    Gson gson = new Gson();
                    List<DeliverGoodsModel> goodsModelList = gson.fromJson(deliverModel.getGoodsModelString(),
                            new TypeToken<List<DeliverGoodsModel>>() {
                            }.getType());
                    for (DeliverGoodsModel temp : goodsModelList) {
                        if (temp != null) {
                            DeliverGoodsPojo deliverGoodsPojo = deliverGoodsController.convertDeliverGoodsPojo(temp, httpSession);
                            if (deliverGoodsPojo != null) {
                                deliverGoodsPojoList.add(deliverGoodsPojo);
                            }
                        }
                    }
                }
                int intResult = businessService.businessDeliver(deliverPojo, deliverGoodsPojoList);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("发货单商务处理成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("发货单商务处理失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("发货单商务处理失败！");
                logger.error(LOG + "：发货单商务处理失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 合同延保处理
     *
     * @param deliverModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/extendBusiness", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel extendBusiness(DeliverModel deliverModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deliverModel != null) {
            try {
                DeliverPojo deliverPojo = deliverController.convertDeliverPojo(deliverModel, httpSession);
                int intResult = businessService.updateTsContract(deliverPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("合同延保处理成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("合同延保处理失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("合同延保处理失败！");
                logger.error(LOG + "：合同延保处理失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

}
