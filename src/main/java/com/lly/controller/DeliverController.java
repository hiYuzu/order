package com.lly.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lly.model.*;
import com.lly.pojo.*;
import com.lly.service.IDeliverService;
import com.lly.service.IInstallService;
import com.lly.util.DateUtil;
import com.lly.util.DefaultParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单控制器
 * @Date: Create in 2018/2/11 10:37
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/DeliverController")
public class DeliverController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "DeliverController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(DeliverController.class);

    @Resource
    private IDeliverService deliverService;

    @Resource
    private IInstallService installService;

    @Resource
    private DeliverGoodsController deliverGoodsController;

    /**
     * 获取待处理单据数量
     *
     * @param deliverType
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getDeliverCount", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public int getDeliverCount(String deliverType, HttpSession httpSession) {
        int result = 0;
        if (deliverType.equals("deliver")) {
            DeliverPojo deliverPojo = new DeliverPojo();
            List<String> statusCodeList = new ArrayList<>();
            statusCodeList.add(DefaultParam.DELIVER_SAVE);
            statusCodeList.add(DefaultParam.DELIVER_REJECT);
            result = deliverService.getDeliverCount(deliverPojo, statusCodeList);
        } else if (deliverType.equals("business")) {
            DeliverPojo deliverPojo = new DeliverPojo();
            List<String> statusCodeList = new ArrayList<>();
            statusCodeList.add(DefaultParam.DELIVER_SUBMIT);
            result = deliverService.getDeliverCount(deliverPojo, statusCodeList);
        } else if (deliverType.equals("install")) {
            GoodsInstallPojo goodsInstallPojo = new GoodsInstallPojo();
            List<String> statusCodeList = new ArrayList<>();
            statusCodeList.add(DefaultParam.DELIVER_BUSINESS);
            statusCodeList.add(DefaultParam.DELIVER_INSTALLING);
            statusCodeList.add(DefaultParam.DELIVER_INSTALLED);
            goodsInstallPojo.setInstallStatus(false);
            result = installService.getInstallGoodsCount(goodsInstallPojo, statusCodeList);
        }
        return result;
    }

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
        DeliverPojo deliverPojo = convertDeliverPojo(deliverMode, httpSession);
        List<String> statusCodeList = new ArrayList<>();
        if (deliverMode.getFinishFlag() != null) {
            if (deliverMode.getFinishFlag()) {
                statusCodeList.add(DefaultParam.DELIVER_SUBMIT);//已提交,待商务
                statusCodeList.add(DefaultParam.DELIVER_BUSINESS);//已商务,待安装
                statusCodeList.add(DefaultParam.DELIVER_INSTALLING);//安装中
                statusCodeList.add(DefaultParam.DELIVER_INSTALLED);//已安装
            } else {
                statusCodeList.add(DefaultParam.DELIVER_SAVE);//已保存,待提交
                statusCodeList.add(DefaultParam.DELIVER_REJECT);//被拒绝,待修改
            }
        }
        int count = deliverService.getDeliverCount(deliverPojo, statusCodeList);
        if (count > 0) {
            deliverPojoList = deliverService.getDeliver(deliverPojo, statusCodeList);
            for (DeliverPojo temp : deliverPojoList) {
                DeliverModel deliverModelTemp = convertDeliverModel(temp);
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
     * 新增发货单
     *
     * @param deliverModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/insertDeliver", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertDeliver(DeliverModel deliverModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deliverModel != null) {
            try {
                if (deliverModel.getOptType() != null && deliverModel.getOptType().equals(DefaultParam.YES)) {
                    deliverModel.setDeliverStatusCode(DefaultParam.DELIVER_SUBMIT);
                    deliverModel.setDeliverStatusName("已提交，待商务");
                } else {
                    deliverModel.setDeliverStatusCode(DefaultParam.DELIVER_SAVE);
                    deliverModel.setDeliverStatusName("已保存，待提交");
                }
                DeliverPojo deliverPojo = convertDeliverPojo(deliverModel, httpSession);
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
                int intResult = deliverService.insertDeliver(deliverPojo, deliverGoodsPojoList);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("新增发货单成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("新增发货单失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增发货单失败！");
                logger.error(LOG + "：新增发货单失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }


    /**
     * 更新发货单
     *
     * @param deliverModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateDeliver", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateDeliver(DeliverModel deliverModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (deliverModel != null) {
            try {
                if (deliverModel.getOptType() != null && deliverModel.getOptType().equals(DefaultParam.YES)) {
                    deliverModel.setDeliverStatusCode(DefaultParam.DELIVER_SUBMIT);
                    deliverModel.setDeliverStatusName("已提交，待商务");
                } else {
                    deliverModel.setDeliverStatusCode(DefaultParam.DELIVER_SAVE);
                    deliverModel.setDeliverStatusName("已保存，待提交");
                }
                if (!deliverService.canDeliverModify(deliverModel.getDeliverId())) {
                    resultModel.setResult(false);
                    resultModel.setDetail("不能更新发货单！");
                    return resultModel;
                }
                DeliverPojo deliverPojo = convertDeliverPojo(deliverModel, httpSession);
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
                int intResult = deliverService.updateDeliver(deliverPojo, deliverGoodsPojoList, false);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("更新发货单成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新发货单失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新发货单失败！");
                logger.error(LOG + "：更新发货单失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 删除发货单
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/deleteDelivers", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteDelivers(@RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                for (String temp : list) {
                    if (!deliverService.canDeliverDelete(temp)) {
                        resultModel.setResult(false);
                        resultModel.setDetail("不能删除发货单！");
                        return resultModel;
                    }
                }
                int intResult = deliverService.deleteDelivers(list);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("删除发货单成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除发货单失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除发货单失败！");
                logger.error(LOG + "：删除发货单失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除发货单失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * 将DeliverPojo转换成DeliverModel
     *
     * @param deliverPojo
     * @return
     */
    public DeliverModel convertDeliverModel(DeliverPojo deliverPojo) {
        DeliverModel deliverModel = new DeliverModel();
        if (deliverPojo != null) {
            deliverModel.setDeliverId(String.valueOf(deliverPojo.getDeliverId()));
            deliverModel.setDeliverType(String.valueOf(deliverPojo.getDeliverType()));
            if (deliverModel.getDeliverType().equals(DefaultParam.OUTSOURCING)) {
                deliverModel.setDeliverTypeName("外购");
            } else {
                deliverModel.setDeliverTypeName("自产");
            }
            deliverModel.setContractCode(deliverPojo.getContractCode());
            if (deliverPojo.getContractType() != null) {
                deliverModel.setContractTypeCode(deliverPojo.getContractType().getParamCode());
                deliverModel.setContractTypeName(deliverPojo.getContractType().getParamName());
            }
            deliverModel.setCustomerName(deliverPojo.getCustomerName());
            deliverModel.setDeliverDate(DateUtil.TimestampToString(deliverPojo.getDeliverDate(), DateUtil.DATA_DAY));
            deliverModel.setWarrantyDate(DateUtil.TimestampToString(deliverPojo.getWarrantyDate(), DateUtil.DATA_DAY));
            deliverModel.setDeliverAddress(deliverPojo.getDeliverAddress());
            if (deliverPojo.getBusinessUser() != null) {
                deliverModel.setBusinessUserId(String.valueOf(deliverPojo.getBusinessUser().getUserId()));
                deliverModel.setBusinessUserName(deliverPojo.getBusinessUser().getUserName());
            }
            deliverModel.setDeliverRemark(deliverPojo.getDeliverRemark());
            if (deliverPojo.getDeliverStatus() != null) {
                deliverModel.setDeliverStatusCode(deliverPojo.getDeliverStatus().getStatusCode());
                deliverModel.setDeliverStatusName(deliverPojo.getDeliverStatus().getStatusName());
            }
            deliverModel.setDeliverNo(deliverPojo.getDeliverNo());
            deliverModel.setContractWarranty(deliverPojo.getContractWarranty());
            deliverModel.setContractAmount(deliverPojo.getContractAmount());
            deliverModel.setContractNewCode(deliverPojo.getContractNewCode());
            deliverModel.setContractBeginTime(DateUtil.TimestampToString(deliverPojo.getContractBeginTime(), DateUtil.DATA_DAY));
            deliverModel.setContractEndTime(DateUtil.TimestampToString(deliverPojo.getContractEndTime(), DateUtil.DATA_DAY));
            deliverModel.setOptUserId(String.valueOf(deliverPojo.getOptUserId()));
            deliverModel.setOptUserName(deliverPojo.getOptUserName());
            deliverModel.setOptTime(DateUtil.TimestampToString(deliverPojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return deliverModel;
    }

    /**
     * 将DeliverModel转换成DeliverPojo
     *
     * @param deliverModel
     * @param httpSession
     * @return
     */
    public DeliverPojo convertDeliverPojo(DeliverModel deliverModel, HttpSession httpSession) {
        DeliverPojo deliverPojo = new DeliverPojo();
        if (deliverModel != null) {
            try {
                if (deliverModel.getDeliverId() != null && !deliverModel.getDeliverId().isEmpty()) {
                    deliverPojo.setDeliverId(Long.valueOf(deliverModel.getDeliverId()));
                }
                if (!StringUtils.isEmpty(deliverModel.getDeliverType())) {
                    deliverPojo.setDeliverType(Integer.valueOf(deliverModel.getDeliverType()));
                }
                deliverPojo.setContractCode(deliverModel.getContractCode());
                ParamPojo contractType = new ParamPojo();
                contractType.setParamCode(deliverModel.getContractTypeCode());
                contractType.setParamName(deliverModel.getContractTypeName());
                deliverPojo.setContractType(contractType);
                deliverPojo.setCustomerName(deliverModel.getCustomerName());
                if (deliverModel.getDeliverDate() != null && !deliverModel.getDeliverDate().isEmpty()) {
                    deliverPojo.setDeliverDate(DateUtil.StringToTimestamp(deliverModel.getDeliverDate(), DateUtil.DATA_DAY));
                }
                if (deliverModel.getWarrantyDate() != null && !deliverModel.getWarrantyDate().isEmpty()) {
                    deliverPojo.setWarrantyDate(DateUtil.StringToTimestamp(deliverModel.getWarrantyDate(), DateUtil.DATA_DAY));
                }
                deliverPojo.setDeliverAddress(deliverModel.getDeliverAddress());
                UserPojo businessUser = new UserPojo();
                if (deliverModel.getBusinessUserId() != null && !deliverModel.getBusinessUserId().isEmpty()) {
                    businessUser.setUserId(Integer.valueOf(deliverModel.getBusinessUserId()));
                }
                businessUser.setUserName(deliverModel.getBusinessUserName());
                deliverPojo.setBusinessUser(businessUser);
                deliverPojo.setDeliverRemark(deliverModel.getDeliverRemark());
                DeliverStatusPojo deliverStatus = new DeliverStatusPojo();
                deliverStatus.setStatusCode(deliverModel.getDeliverStatusCode());
                deliverStatus.setStatusName(deliverModel.getDeliverStatusName());
                deliverPojo.setDeliverStatus(deliverStatus);
                deliverPojo.setDeliverNo(deliverModel.getDeliverNo());
                deliverPojo.setContractWarranty(deliverModel.getContractWarranty());
                deliverPojo.setContractAmount(deliverModel.getContractAmount());
                deliverPojo.setContractNewCode(deliverModel.getContractNewCode());
                if (deliverModel.getContractBeginTime() != null && !deliverModel.getContractBeginTime().isEmpty()) {
                    deliverPojo.setContractBeginTime(DateUtil.StringToTimestamp(deliverModel.getContractBeginTime(), DateUtil.DATA_DAY));
                }
                if (deliverModel.getContractEndTime() != null && !deliverModel.getContractEndTime().isEmpty()) {
                    deliverPojo.setContractEndTime(DateUtil.StringToTimestamp(deliverModel.getContractEndTime(), DateUtil.DATA_DAY));
                }
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
                    deliverPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
                }
                if (deliverModel.getOptTime() != null && !deliverModel.getOptTime().isEmpty()) {
                    deliverPojo.setOptTime(DateUtil.StringToTimestamp(deliverModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
                } else {
                    deliverPojo.setOptTime(DateUtil.GetSystemDateTime(0));
                }
                deliverPojo.setRowCount(deliverModel.getRowCount());
                deliverPojo.setRowIndex(deliverModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将DeliverMode转换成DeliverPojo失败，信息为" + e.getMessage());
            }
        }
        return deliverPojo;
    }


}
