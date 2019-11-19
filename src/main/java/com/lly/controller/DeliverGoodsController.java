package com.lly.controller;

import com.lly.model.DeliverGoodsModel;
import com.lly.model.ResultListModel;
import com.lly.model.ResultModel;
import com.lly.model.UserModel;
import com.lly.pojo.AssemblePojo;
import com.lly.pojo.DeliverGoodsPojo;
import com.lly.pojo.DeliverPojo;
import com.lly.pojo.ParamPojo;
import com.lly.service.IDeliverGoodsService;
import com.lly.util.DateUtil;
import com.lly.util.DefaultParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单货物控制器
 * @Date: Create in 2018/2/12 15:58
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/DeliverGoodsController")
public class DeliverGoodsController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DeliverGoodsController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeliverGoodsController.class);

    @Resource
    private IDeliverGoodsService deliverGoodsService;

    /**
     * 获取发货单货物
     *
     * @param deliverGoodsModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getGoodsDeliver", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeliverGoodsModel> getGoodsDeliver(DeliverGoodsModel deliverGoodsModel, HttpSession httpSession) {
        ResultListModel<DeliverGoodsModel> resultListModel = new ResultListModel<DeliverGoodsModel>();
        List<DeliverGoodsModel> deliverGoodsModelList = new ArrayList<DeliverGoodsModel>();
        List<DeliverGoodsPojo> deliverGoodsPojoList;
        DeliverGoodsPojo deliverGoodsPojo = convertDeliverGoodsPojo(deliverGoodsModel, httpSession);
        int count = deliverGoodsService.getDeliverGoodsCount(deliverGoodsPojo);
        if (count > 0) {
            deliverGoodsPojoList = deliverGoodsService.getDeliverGoods(deliverGoodsPojo);
            for (DeliverGoodsPojo deliverGoodsPojoTemp : deliverGoodsPojoList) {
                DeliverGoodsModel deliverGoodsModelTemp = convertDeliverGoodsModel(deliverGoodsPojoTemp);
                if (deliverGoodsModelTemp != null) {
                    deliverGoodsModelList.add(deliverGoodsModelTemp);
                }
            }
        }
        resultListModel.setData(deliverGoodsModelList);
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }


    /**
     * 更新发货单状态
     *
     * @param deliverGoodsModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateDeliverGoodsStatus", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDeliverGoodsStatus(DeliverGoodsModel deliverGoodsModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deliverGoodsModel != null) {
            try {
                DeliverGoodsPojo deliverGoodsPojo = convertDeliverGoodsPojo(deliverGoodsModel, httpSession);
                int intResult = deliverGoodsService.updateDeliverGoodsStatus(deliverGoodsPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("更新发货设备状态成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新发货设备状态失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新发货设备状态失败！");
                logger.error(LOG + "：更新发货设备状态失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新发货单备注
     *
     * @param deliverGoodsModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateDeliverGoodsMemo", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDeliverGoodsMemo(DeliverGoodsModel deliverGoodsModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deliverGoodsModel != null) {
            try {
                DeliverGoodsPojo deliverGoodsPojo = convertDeliverGoodsPojo(deliverGoodsModel, httpSession);
                int intResult = deliverGoodsService.updateDeliverGoodsMemo(deliverGoodsPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("更新发货设备备注成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新发货设备备注失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新发货设备备注失败！");
                logger.error(LOG + "：更新发货设备备注失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 获取发货单货物（通过发货单ID）
     *
     * @param deliverId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getGoodsDeliverById", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeliverGoodsModel> getGoodsDeliverById(String deliverId, HttpSession httpSession) {
        ResultListModel<DeliverGoodsModel> resultListModel = new ResultListModel<DeliverGoodsModel>();
        List<DeliverGoodsModel> deliverGoodsModelList = new ArrayList<DeliverGoodsModel>();
        List<DeliverGoodsPojo> deliverGoodsPojoList;
        deliverGoodsPojoList = deliverGoodsService.getDeliverGoodsByDeliverId(deliverId);
        for (DeliverGoodsPojo deliverGoodsPojoTemp : deliverGoodsPojoList) {
            DeliverGoodsModel deliverGoodsModelTemp = convertDeliverGoodsModel(deliverGoodsPojoTemp);
            if (deliverGoodsModelTemp != null) {
                deliverGoodsModelList.add(deliverGoodsModelTemp);
            }
        }

        resultListModel.setData(deliverGoodsModelList);
        resultListModel.setCount(deliverGoodsModelList.size());
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 将DeliverGoodsPojo转换成DeliverGoodsModel
     *
     * @param deliverGoodsPojo
     * @return
     */
    public DeliverGoodsModel convertDeliverGoodsModel(DeliverGoodsPojo deliverGoodsPojo) {
        DeliverGoodsModel deliverGoodsModel = new DeliverGoodsModel();
        if (deliverGoodsPojo != null) {
            deliverGoodsModel.setGoodsId(String.valueOf(deliverGoodsPojo.getGoodsId()));
            if (deliverGoodsPojo.getDeliverPojo() != null) {
                deliverGoodsModel.setDeliverId(String.valueOf(deliverGoodsPojo.getDeliverPojo().getDeliverId()));
            }
            if (deliverGoodsPojo.getAssemblePojo() != null) {
                deliverGoodsModel.setAssembleId(String.valueOf(deliverGoodsPojo.getAssemblePojo().getAssembleId()));
            }
            if (deliverGoodsPojo.getParamPojo() != null) {
                deliverGoodsModel.setPnCode(deliverGoodsPojo.getParamPojo().getParamCode());
                deliverGoodsModel.setPnName(deliverGoodsPojo.getParamPojo().getParamName());
            }
            deliverGoodsModel.setSnCode(deliverGoodsPojo.getSnCode());
            deliverGoodsModel.setInstallEnterprise(deliverGoodsPojo.getInstallEnterprise());
            deliverGoodsModel.setJobNo(deliverGoodsPojo.getJobNo());
            deliverGoodsModel.setAnalyzerNumber(deliverGoodsPojo.getAnalyzerNumber());
            deliverGoodsModel.setGoodsAmount(deliverGoodsPojo.getGoodsAmount());
            if (deliverGoodsPojo.getGoodsStatus()) {
                deliverGoodsModel.setGoodsStatusName("退货");
            } else {
                deliverGoodsModel.setGoodsStatusName("正常");
            }
            deliverGoodsModel.setGoodsMemo(deliverGoodsPojo.getGoodsMemo());
            deliverGoodsModel.setReturnMemo(deliverGoodsPojo.getReturnMemo());
            //外购相关
            deliverGoodsModel.setGoodsName(deliverGoodsPojo.getGoodsName());
            deliverGoodsModel.setPurchaseContract(deliverGoodsPojo.getPurchaseContract());
            deliverGoodsModel.setStorageDate(deliverGoodsPojo.getStorageDate());
            deliverGoodsModel.setGoodsSupplier(deliverGoodsPojo.getGoodsSupplier());
            deliverGoodsModel.setDeliverPoint(deliverGoodsPojo.getDeliverPoint());
            deliverGoodsModel.setWarrantyClause(deliverGoodsPojo.getWarrantyClause());
            deliverGoodsModel.setOptUserId(String.valueOf(deliverGoodsPojo.getOptUserId()));
            deliverGoodsModel.setOptUserName(deliverGoodsPojo.getOptUserName());
            deliverGoodsModel.setOptTime(DateUtil.TimestampToString(deliverGoodsPojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return deliverGoodsModel;
    }

    /**
     * 将DeliverGoodsModel转换成DeliverGoodsPojo
     *
     * @param deliverGoodsModel
     * @param httpSession
     * @return
     */
    public DeliverGoodsPojo convertDeliverGoodsPojo(DeliverGoodsModel deliverGoodsModel, HttpSession httpSession) {
        DeliverGoodsPojo deliverGoodsPojo = new DeliverGoodsPojo();
        if (deliverGoodsModel != null) {
            if (deliverGoodsModel.getGoodsId() != null && !deliverGoodsModel.getGoodsId().isEmpty()) {
                deliverGoodsPojo.setGoodsId(Long.valueOf(deliverGoodsModel.getGoodsId()));
            }
            DeliverPojo deliverPojo = new DeliverPojo();
            if (!StringUtils.isEmpty(deliverGoodsModel.getDeliverId())) {
                deliverPojo.setDeliverId(Long.valueOf(deliverGoodsModel.getDeliverId()));
            }
            deliverGoodsPojo.setDeliverPojo(deliverPojo);
            AssemblePojo assemblePojo = new AssemblePojo();
            if (!StringUtils.isEmpty(deliverGoodsModel.getAssembleId())) {
                assemblePojo.setAssembleId(Long.valueOf(deliverGoodsModel.getAssembleId()));

            }
            deliverGoodsPojo.setAssemblePojo(assemblePojo);
            ParamPojo paramPojo = new ParamPojo();
            paramPojo.setParamCode(deliverGoodsModel.getPnCode());
            paramPojo.setParamName(deliverGoodsModel.getPnName());
            deliverGoodsPojo.setParamPojo(paramPojo);
            deliverGoodsPojo.setSnCode(deliverGoodsModel.getSnCode());
            deliverGoodsPojo.setInstallEnterprise(deliverGoodsModel.getInstallEnterprise());
            deliverGoodsPojo.setJobNo(deliverGoodsModel.getJobNo());
            deliverGoodsPojo.setAnalyzerNumber(deliverGoodsModel.getAnalyzerNumber());
            deliverGoodsPojo.setGoodsAmount(deliverGoodsModel.getGoodsAmount());
            deliverGoodsPojo.setGoodsStatus(deliverGoodsModel.getGoodsStatus());
            deliverGoodsPojo.setGoodsMemo(deliverGoodsModel.getGoodsMemo());
            deliverGoodsPojo.setReturnMemo(deliverGoodsModel.getReturnMemo());
            //外购相关
            deliverGoodsPojo.setGoodsName(deliverGoodsModel.getGoodsName());
            deliverGoodsPojo.setPurchaseContract(deliverGoodsModel.getPurchaseContract());
            deliverGoodsPojo.setStorageDate(deliverGoodsModel.getStorageDate());
            deliverGoodsPojo.setGoodsSupplier(deliverGoodsModel.getGoodsSupplier());
            deliverGoodsPojo.setDeliverPoint(deliverGoodsModel.getDeliverPoint());
            deliverGoodsPojo.setWarrantyClause(deliverGoodsModel.getWarrantyClause());
            UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
            if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
                deliverGoodsPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
            }
            if (deliverGoodsModel.getOptTime() != null && !deliverGoodsModel.getOptTime().isEmpty()) {
                deliverGoodsPojo.setOptTime(DateUtil.StringToTimestamp(deliverGoodsModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
            } else {
                deliverGoodsPojo.setOptTime(DateUtil.GetSystemDateTime(0));
            }
            deliverGoodsPojo.setRowCount(deliverGoodsModel.getRowCount());
            deliverGoodsPojo.setRowIndex(deliverGoodsModel.getRowIndex());
        }
        return deliverGoodsPojo;
    }

}
