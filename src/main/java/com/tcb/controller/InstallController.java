package com.tcb.controller;

import com.tcb.model.GoodsInstallModel;
import com.tcb.model.ResultListModel;
import com.tcb.model.ResultModel;
import com.tcb.model.UserModel;
import com.tcb.pojo.DeliverGoodsPojo;
import com.tcb.pojo.DeliverPojo;
import com.tcb.pojo.GoodsInstallPojo;
import com.tcb.pojo.ParamPojo;
import com.tcb.service.IInstallService;
import com.tcb.util.DateUtil;
import com.tcb.util.DefaultParam;
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
 * @Description: 货物安装控制器
 * @Date: Create in 2018/2/27 14:14
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/InstallController")
public class InstallController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "InstallController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(InstallController.class);

    @Resource
    private IInstallService installService;

    /**
     * 获取安装货物
     *
     * @param goodsInstallModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getGoodsInstall", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<GoodsInstallModel> getGoodsInstall(GoodsInstallModel goodsInstallModel, HttpSession httpSession) {
        ResultListModel<GoodsInstallModel> resultListModel = new ResultListModel<GoodsInstallModel>();
        List<GoodsInstallModel> goodsInstallModelList = new ArrayList<GoodsInstallModel>();
        List<GoodsInstallPojo> goodsInstallPojoList;
        GoodsInstallPojo goodsInstallPojo = convertGoodsInstallPojo(goodsInstallModel, httpSession);
        List<String> deliverStatusCodeList = new ArrayList<>();
        deliverStatusCodeList.add(DefaultParam.DELIVER_BUSINESS);
        deliverStatusCodeList.add(DefaultParam.DELIVER_INSTALLING);
        deliverStatusCodeList.add(DefaultParam.DELIVER_INSTALLED);
        int count = installService.getInstallGoodsCount(goodsInstallPojo, deliverStatusCodeList);
        if (count > 0) {
            goodsInstallPojoList = installService.getInstallGoods(goodsInstallPojo, deliverStatusCodeList);
            for (GoodsInstallPojo deliverGoodsPojoTemp : goodsInstallPojoList) {
                GoodsInstallModel goodsInstallModelTemp = convertGoodsInstallModel(deliverGoodsPojoTemp);
                if (goodsInstallModelTemp != null) {
                    goodsInstallModelList.add(goodsInstallModelTemp);
                }
            }
        }
        resultListModel.setData(goodsInstallModelList);
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 货物安装处理
     *
     * @param goodsInstallModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/operateGoodsInstall", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel operateGoodsInstall(GoodsInstallModel goodsInstallModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (goodsInstallModel != null) {
            try {
                GoodsInstallPojo goodsInstallPojo = convertGoodsInstallPojo(goodsInstallModel, httpSession);
                int intResult = installService.operateInstallGoods(goodsInstallPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("货物安装处理成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("货物安装处理失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("货物安装处理失败！");
                logger.error(LOG + "：货物安装处理失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 将GoodsInstallPojo转换成GoodsInstallModel
     *
     * @param goodsInstallPojo
     * @return
     */
    public GoodsInstallModel convertGoodsInstallModel(GoodsInstallPojo goodsInstallPojo) {
        GoodsInstallModel goodsInstallModel = new GoodsInstallModel();
        if (goodsInstallPojo != null) {
            goodsInstallModel.setInstallId(String.valueOf(goodsInstallPojo.getInstallId()));
            if (goodsInstallPojo.getDeliverGoodsPojo() != null) {
                goodsInstallModel.setGoodsId(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getGoodsId()));
                goodsInstallModel.setInstallEnterprise(goodsInstallPojo.getDeliverGoodsPojo().getInstallEnterprise());
                goodsInstallModel.setSnCode(goodsInstallPojo.getDeliverGoodsPojo().getSnCode());
                goodsInstallModel.setAnalyzerNumber(goodsInstallPojo.getDeliverGoodsPojo().getAnalyzerNumber());
                goodsInstallModel.setGoodsMemo(goodsInstallPojo.getDeliverGoodsPojo().getGoodsMemo());
                goodsInstallModel.setWarrantyClause(goodsInstallPojo.getDeliverGoodsPojo().getWarrantyClause());
                if (goodsInstallPojo.getDeliverGoodsPojo().getParamPojo() != null) {
                    goodsInstallModel.setPnCode(goodsInstallPojo.getDeliverGoodsPojo().getParamPojo().getParamCode());
                    goodsInstallModel.setPnName(goodsInstallPojo.getDeliverGoodsPojo().getParamPojo().getParamName());
                }
                if (goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo() != null) {
                    goodsInstallModel.setDeliverId(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getDeliverId()));
                    goodsInstallModel.setDeliverType(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getDeliverType()));
                    if (goodsInstallModel.getDeliverType().equals(DefaultParam.OUTSOURCING)) {
                        goodsInstallModel.setDeliverTypeName("外购");
                    } else {
                        goodsInstallModel.setDeliverTypeName("自产");
                    }
                    goodsInstallModel.setContractCode(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getContractCode());
                    goodsInstallModel.setContractWarranty(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getContractWarranty());
                }
                if (goodsInstallPojo.getDeliverGoodsPojo().getAssemblePojo() != null) {
                    goodsInstallModel.setAssembleId(String.valueOf(goodsInstallPojo.getDeliverGoodsPojo().getAssemblePojo().getAssembleId()));
                }
                if (goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getContractType() != null) {
                    goodsInstallModel.setContractTypeCode(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getContractType().getParamCode());
                    goodsInstallModel.setContractTypeName(goodsInstallPojo.getDeliverGoodsPojo().getDeliverPojo().getContractType().getParamName());
                }
            }
            goodsInstallModel.setInstallUser(goodsInstallPojo.getInstallUser());
            goodsInstallModel.setBeginTime(DateUtil.TimestampToString(goodsInstallPojo.getBeginTime(), DateUtil.DATA_DAY));
            goodsInstallModel.setEndTime(DateUtil.TimestampToString(goodsInstallPojo.getEndTime(), DateUtil.DATA_DAY));
            goodsInstallModel.setWarrantyPeriod(DateUtil.TimestampToString(goodsInstallPojo.getWarrantyPeriod(), DateUtil.DATA_DAY));
            goodsInstallModel.setInstallStatusCode(goodsInstallPojo.getInstallStatus());
            if (goodsInstallPojo.getInstallStatus() != null && goodsInstallPojo.getInstallStatus()) {
                goodsInstallModel.setInstallStatusName("完成");
            } else {
                goodsInstallModel.setInstallStatusName("未完成");
            }
            goodsInstallModel.setContractNewCode(goodsInstallPojo.getContractNewCode());
            goodsInstallModel.setDeliverDate(DateUtil.TimestampToString(goodsInstallPojo.getDeliverDate(), DateUtil.DATA_DAY));
            goodsInstallModel.setContractBeginTime(DateUtil.TimestampToString(goodsInstallPojo.getContractBeginTime(), DateUtil.DATA_DAY));
            goodsInstallModel.setContractEndTime(DateUtil.TimestampToString(goodsInstallPojo.getContractEndTime(), DateUtil.DATA_DAY));
            goodsInstallModel.setInstrumentBrand(goodsInstallPojo.getInstrumentBrand());
            goodsInstallModel.setInstallMemo(goodsInstallPojo.getInstallMemo());
            goodsInstallModel.setOptUserId(String.valueOf(goodsInstallPojo.getOptUserId()));
            goodsInstallModel.setOptUserName(goodsInstallPojo.getOptUserName());
            goodsInstallModel.setOptTime(DateUtil.TimestampToString(goodsInstallPojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return goodsInstallModel;
    }

    /**
     * 将GoodsInstallModel转换成GoodsInstallPojo
     *
     * @param goodsInstallModel
     * @param httpSession
     * @return
     */
    public GoodsInstallPojo convertGoodsInstallPojo(GoodsInstallModel goodsInstallModel, HttpSession httpSession) {
        GoodsInstallPojo goodsInstallPojo = new GoodsInstallPojo();
        if (goodsInstallModel != null) {
            if (goodsInstallModel.getInstallId() != null && !goodsInstallModel.getInstallId().isEmpty()) {
                goodsInstallPojo.setInstallId(Long.valueOf(goodsInstallModel.getInstallId()));
            }
            DeliverGoodsPojo deliverGoodsPojo = new DeliverGoodsPojo();
            if (goodsInstallModel.getGoodsId() != null && !goodsInstallModel.getGoodsId().isEmpty()) {
                deliverGoodsPojo.setGoodsId(Long.valueOf(goodsInstallModel.getGoodsId()));
            }
            deliverGoodsPojo.setInstallEnterprise(goodsInstallModel.getInstallEnterprise());
            deliverGoodsPojo.setSnCode(goodsInstallModel.getSnCode());
            DeliverPojo deliverPojo = new DeliverPojo();
            if (goodsInstallModel.getDeliverId() != null && !goodsInstallModel.getDeliverId().isEmpty()) {
                deliverPojo.setDeliverId(Long.valueOf(goodsInstallModel.getDeliverId()));
            }
            if (!StringUtils.isEmpty(goodsInstallModel.getDeliverType())) {
                deliverPojo.setDeliverType(Integer.valueOf(goodsInstallModel.getDeliverType()));
            }
            deliverPojo.setContractCode(goodsInstallModel.getContractCode());
            deliverPojo.setContractWarranty(goodsInstallModel.getContractWarranty());
            ParamPojo contractType = new ParamPojo();
            contractType.setParamCode(goodsInstallModel.getContractTypeCode());
            contractType.setParamName(goodsInstallModel.getContractTypeName());
            deliverPojo.setContractType(contractType);
            deliverGoodsPojo.setDeliverPojo(deliverPojo);
            ParamPojo paramPojo = new ParamPojo();
            paramPojo.setParamCode(goodsInstallModel.getPnCode());
            paramPojo.setParamName(goodsInstallModel.getPnName());
            deliverGoodsPojo.setParamPojo(paramPojo);
            goodsInstallPojo.setDeliverGoodsPojo(deliverGoodsPojo);
            goodsInstallPojo.setInstallUser(goodsInstallModel.getInstallUser());
            if (goodsInstallModel.getBeginTime() != null && !goodsInstallModel.getBeginTime().isEmpty()) {
                goodsInstallPojo.setBeginTime(DateUtil.StringToTimestamp(goodsInstallModel.getBeginTime(), DateUtil.DATA_DAY));
            }
            if (goodsInstallModel.getEndTime() != null && !goodsInstallModel.getEndTime().isEmpty()) {
                goodsInstallPojo.setEndTime(DateUtil.StringToTimestamp(goodsInstallModel.getEndTime(), DateUtil.DATA_DAY));
            }
            if (goodsInstallModel.getWarrantyPeriod() != null && !goodsInstallModel.getWarrantyPeriod().isEmpty()) {
                goodsInstallPojo.setWarrantyPeriod(DateUtil.StringToTimestamp(goodsInstallModel.getWarrantyPeriod(), DateUtil.DATA_DAY));
            }
            goodsInstallPojo.setInstallStatus(goodsInstallModel.getInstallStatusCode());
            goodsInstallPojo.setInstrumentBrand(goodsInstallModel.getInstrumentBrand());
            goodsInstallPojo.setInstallMemo(goodsInstallModel.getInstallMemo());
            UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
            if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
                goodsInstallPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
            }
            if (goodsInstallModel.getOptTime() != null && !goodsInstallModel.getOptTime().isEmpty()) {
                goodsInstallPojo.setOptTime(DateUtil.StringToTimestamp(goodsInstallModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
            } else {
                goodsInstallPojo.setOptTime(DateUtil.GetSystemDateTime(0));
            }
            goodsInstallPojo.setRowCount(goodsInstallModel.getRowCount());
            goodsInstallPojo.setRowIndex(goodsInstallModel.getRowIndex());
        }
        return goodsInstallPojo;
    }

}
