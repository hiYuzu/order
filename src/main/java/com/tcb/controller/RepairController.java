package com.tcb.controller;

import com.tcb.model.*;
import com.tcb.pojo.*;
import com.tcb.service.IRepairService;
import com.tcb.util.DateUtil;
import com.tcb.util.DefaultParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 设备维修服控制器
 * @Date: Create in 2018/3/2 9:10
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/RepairController")
public class RepairController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "RepairController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(RepairController.class);

    @Resource
    private IRepairService repairService;

    /**
     * 获取设备维修记录
     * @param goodsRepairModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getGoodsRepair", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<GoodsRepairModel> getGoodsRepair(GoodsRepairModel goodsRepairModel, HttpSession httpSession) {
        ResultListModel<GoodsRepairModel> resultListModel = new ResultListModel<GoodsRepairModel>();
        List<GoodsRepairModel> goodsRepairModelList = new ArrayList<GoodsRepairModel>();
        List<GoodsRepairPojo> goodsRepairPojoList;
        GoodsRepairPojo goodsRepairPojo = convertGoodsRepairPojo(goodsRepairModel, httpSession);
        int count = repairService.getRepairGoodsCount(goodsRepairPojo);
        if (count > 0) {
            goodsRepairPojoList = repairService.getRepairGoods(goodsRepairPojo);
            for (GoodsRepairPojo goodsRepairPojoTemp : goodsRepairPojoList) {
                GoodsRepairModel goodsInstallModelTemp = convertGoodsRepairModel(goodsRepairPojoTemp);
                if (goodsInstallModelTemp != null) {
                    goodsRepairModelList.add(goodsInstallModelTemp);
                }
            }
        }
        resultListModel.setData(goodsRepairModelList);
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }


    /**
     * 新增设备维修记录
     * @param goodsRepairModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/insertGoodsRepair", method = { RequestMethod.POST })
    @ResponseBody
    public ResultModel insertGoodsRepair(GoodsRepairModel goodsRepairModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (goodsRepairModel != null) {
            try {
                GoodsRepairPojo goodsRepairPojo = convertGoodsRepairPojo(goodsRepairModel,httpSession);
                int intResult = repairService.insertRepairGoods(goodsRepairPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("新增设备维修记录成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("新增设备维修记录失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增设备维修记录失败！");
                logger.error(LOG + "：新增设备维修记录失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新设备维修记录
     * @param goodsRepairModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateGoodsRepair", method = { RequestMethod.POST })
    @ResponseBody
    public ResultModel updateGoodsRepair(GoodsRepairModel goodsRepairModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (goodsRepairModel != null) {
            try {
                GoodsRepairPojo goodsRepairPojo = convertGoodsRepairPojo(goodsRepairModel,httpSession);
                int intResult = repairService.updateRepairGoods(goodsRepairPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("更新设备维修记录成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新设备维修记录失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新设备维修记录失败！");
                logger.error(LOG + "：更新设备维修记录失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 删除设备维修记录
     * @param repairId
     * @return
     */
    @RequestMapping(value = "/deleteGoodsRepair", method = { RequestMethod.POST })
    @ResponseBody
    public ResultModel deleteGoodsRepair(String repairId) {
        ResultModel resultModel = new ResultModel();
        if (repairId != null && !repairId.isEmpty()) {
            try {
                int intResult = repairService.deleteRepairGoods(repairId);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("删除设备维修记录成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除设备维修记录失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除设备维修记录失败！");
                logger.error(LOG + "：删除设备维修记录失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除设备维修记录失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * 将GoodsRepairPojo转换成GoodsRepairModel
     *
     * @param goodsRepairPojo
     * @return
     */
    public GoodsRepairModel convertGoodsRepairModel(GoodsRepairPojo goodsRepairPojo) {
        GoodsRepairModel goodsRepairModel = new GoodsRepairModel();
        if (goodsRepairPojo != null) {
            goodsRepairModel.setRepairId(String.valueOf(goodsRepairPojo.getRepairId()));
            if (goodsRepairPojo.getGoodsInstallPojo() != null) {
                goodsRepairModel.setInstallId(String.valueOf(goodsRepairPojo.getGoodsInstallPojo().getInstallId()));
                goodsRepairModel.setWarrantyPeriod(DateUtil.TimestampToString(goodsRepairPojo.getGoodsInstallPojo().getWarrantyPeriod(), DateUtil.DATA_DAY));
                if(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo() != null){
                    goodsRepairModel.setGoodsId(String.valueOf(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getGoodsId()));
                    goodsRepairModel.setInstallEnterprise(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getInstallEnterprise());
                    goodsRepairModel.setSnCode(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getSnCode());
                    if(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getParamPojo() != null){
                        goodsRepairModel.setPnCode(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getParamPojo().getParamCode());
                        goodsRepairModel.setPnName(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getParamPojo().getParamName());
                    }
                    if(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo() != null){
                        goodsRepairModel.setDeliverId(String.valueOf(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo().getDeliverId()));
                        goodsRepairModel.setDeliverType(String.valueOf(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo().getDeliverType()));
                        goodsRepairModel.setContractCode(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo().getContractCode());
                        goodsRepairModel.setContractWarranty(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo().getContractWarranty());
                        if(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo().getContractType() != null){
                            goodsRepairModel.setContractTypeCode(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo().getContractType().getParamCode());
                            goodsRepairModel.setContractTypeName(goodsRepairPojo.getGoodsInstallPojo().getDeliverGoodsPojo().getDeliverPojo().getContractType().getParamName());
                        }
                    }
                }
            }
            goodsRepairModel.setRepairUser(goodsRepairPojo.getRepairUser());
            goodsRepairModel.setRepairTime(DateUtil.TimestampToString(goodsRepairPojo.getRepairTime(), DateUtil.DATA_DAY));
            goodsRepairModel.setMaterielName(goodsRepairPojo.getMaterielName());
            goodsRepairModel.setMaterielNumber(goodsRepairPojo.getMaterielNumber());
            goodsRepairModel.setOptUserId(String.valueOf(goodsRepairPojo.getOptUserId()));
            goodsRepairModel.setOptUserName(goodsRepairPojo.getOptUserName());
            goodsRepairModel.setOptTime(DateUtil.TimestampToString(goodsRepairPojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return goodsRepairModel;
    }

    /**
     * 将GoodsRepairModel转换成GoodsRepairPojo
     *
     * @param goodsRepairModel
     * @param httpSession
     * @return
     */
    public GoodsRepairPojo convertGoodsRepairPojo(GoodsRepairModel goodsRepairModel, HttpSession httpSession) {
        GoodsRepairPojo goodsRepairPojo = new GoodsRepairPojo();
        if (goodsRepairModel != null) {
            if (goodsRepairModel.getRepairId() != null && !goodsRepairModel.getRepairId().isEmpty()) {
                goodsRepairPojo.setRepairId(Long.valueOf(goodsRepairModel.getRepairId()));
            }
            GoodsInstallPojo goodsInstallPojo = new GoodsInstallPojo();
            if (goodsRepairModel.getInstallId() != null && !goodsRepairModel.getInstallId().isEmpty()) {
                goodsInstallPojo.setInstallId(Long.valueOf(goodsRepairModel.getInstallId()));
            }
            if(goodsRepairModel.getWarrantyPeriod() != null && !goodsRepairModel.getWarrantyPeriod().isEmpty()){
                goodsInstallPojo.setWarrantyPeriod(DateUtil.StringToTimestamp(goodsRepairModel.getWarrantyPeriod(),DateUtil.DATA_DAY));
            }
            DeliverGoodsPojo deliverGoodsPojo = new DeliverGoodsPojo();
            if(goodsRepairModel.getGoodsId() != null && !goodsRepairModel.getGoodsId().isEmpty()){
                deliverGoodsPojo.setGoodsId(Long.valueOf(goodsRepairModel.getGoodsId()));
            }
            deliverGoodsPojo.setInstallEnterprise(goodsRepairModel.getInstallEnterprise());
            deliverGoodsPojo.setSnCode(goodsRepairModel.getSnCode());
            ParamPojo paramPojo = new ParamPojo();
            paramPojo.setParamCode(goodsRepairModel.getPnCode());
            paramPojo.setParamName(goodsRepairModel.getPnName());
            deliverGoodsPojo.setParamPojo(paramPojo);
            DeliverPojo deliverPojo = new DeliverPojo();
            if (goodsRepairModel.getDeliverId() != null && !goodsRepairModel.getDeliverId().isEmpty()) {
                deliverPojo.setDeliverId(Long.valueOf(goodsRepairModel.getDeliverId()));
            }
            deliverPojo.setContractCode(goodsRepairModel.getContractCode());
            deliverPojo.setContractWarranty(goodsRepairModel.getContractWarranty());
            ParamPojo contractType = new ParamPojo();
            contractType.setParamCode(goodsRepairModel.getContractTypeCode());
            contractType.setParamName(goodsRepairModel.getContractTypeName());
            deliverPojo.setContractType(contractType);
            deliverGoodsPojo.setDeliverPojo(deliverPojo);
            goodsInstallPojo.setDeliverGoodsPojo(deliverGoodsPojo);
            if(goodsRepairModel.getWarrantyPeriod() != null && !goodsRepairModel.getWarrantyPeriod().isEmpty()){
                goodsInstallPojo.setWarrantyPeriod(DateUtil.StringToTimestamp(goodsRepairModel.getWarrantyPeriod(), DateUtil.DATA_DAY));
            }
            goodsRepairPojo.setGoodsInstallPojo(goodsInstallPojo);
            goodsRepairPojo.setRepairUser(goodsRepairModel.getRepairUser());
            if(goodsRepairModel.getRepairTime() != null && !goodsRepairModel.getRepairTime().isEmpty()){
                goodsRepairPojo.setRepairTime(DateUtil.StringToTimestamp(goodsRepairModel.getRepairTime(), DateUtil.DATA_DAY));
            }
            goodsRepairPojo.setMaterielName(goodsRepairModel.getMaterielName());
            goodsRepairPojo.setMaterielNumber(goodsRepairModel.getMaterielNumber());
            UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
            if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
                goodsRepairPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
            }
            if (goodsRepairModel.getOptTime() != null && !goodsRepairModel.getOptTime().isEmpty()) {
                goodsRepairPojo.setOptTime(DateUtil.StringToTimestamp(goodsRepairModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
            } else {
                goodsRepairPojo.setOptTime(DateUtil.GetSystemDateTime(0));
            }
            goodsRepairPojo.setRowCount(goodsRepairModel.getRowCount());
            goodsRepairPojo.setRowIndex(goodsRepairModel.getRowIndex());
        }
        return goodsRepairPojo;
    }

}
