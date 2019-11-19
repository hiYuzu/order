package com.tcb.controller;

import com.tcb.model.AssembleOldModel;
import com.tcb.model.ResultListModel;
import com.tcb.model.ResultModel;
import com.tcb.model.UserModel;
import com.tcb.pojo.*;
import com.tcb.service.IAssembleOldService;
import com.tcb.service.IAssembleTestService;
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
 * @Description: 组装老化控制器
 * @Date: Create in 2019/3/28 13:53
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/AssembleOldController")
public class AssembleOldController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AssembleOldController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AssembleOldController.class);

    @Resource
    private IAssembleOldService assembleOldService;

    @Resource
    private IAssembleTestService assembleTestService;

    /**
     * 获取组装老化信息
     *
     * @param assembleOldModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAssembleOld", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AssembleOldModel> getAssembleOld(AssembleOldModel assembleOldModel, HttpSession httpSession) {
        ResultListModel<AssembleOldModel> resultListModel = new ResultListModel<AssembleOldModel>();
        List<AssembleOldModel> assembleOldModelList = new ArrayList<AssembleOldModel>();
        List<AssembleOldPojo> assembleOldPojoList;
        AssembleOldPojo assembleOldPojo = convertAssembleOldPojo(assembleOldModel, httpSession);
        List<String> statusCodeList = new ArrayList<>();
        if (assembleOldModel.getFinishFlag() != null) {
            if (assembleOldModel.getFinishFlag()) {
                statusCodeList.add(DefaultParam.PRODUCT_OLDED);//已老化,待发货
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER);//已发货
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER_RESET);//已重置,待发货
            } else {
                statusCodeList.add(DefaultParam.PRODUCT_TESTED);//已测试,待老化
                statusCodeList.add(DefaultParam.PRODUCT_OLD);//老化中
            }
        }else{
            statusCodeList.add(DefaultParam.PRODUCT_OLDED);//已老化,待发货
            statusCodeList.add(DefaultParam.PRODUCT_DELIVER);//已发货
            statusCodeList.add(DefaultParam.PRODUCT_DELIVER_RESET);//已重置,待发货
            statusCodeList.add(DefaultParam.PRODUCT_TESTED);//已测试,待老化
            statusCodeList.add(DefaultParam.PRODUCT_OLD);//老化中
        }
        int count = assembleOldService.getAssembleOldCount(assembleOldPojo, statusCodeList);
        if (count > 0) {
            assembleOldPojoList = assembleOldService.getAssembleOld(assembleOldPojo, statusCodeList);
            for (AssembleOldPojo temp : assembleOldPojoList) {
                AssembleOldModel assembleOldModelTemp = convertAssembleOldModel(temp);
                if (assembleOldModelTemp != null) {
                    assembleOldModelList.add(assembleOldModelTemp);
                }
            }
            resultListModel.setData(assembleOldModelList);
        }
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }


    /**
     * 将AssembleOldModel转换成AssembleOldPojo
     *
     * @param assembleOldModel
     * @param httpSession
     * @return
     */
    public AssembleOldPojo convertAssembleOldPojo(AssembleOldModel assembleOldModel, HttpSession httpSession) {
        AssembleOldPojo assembleOldPojo = new AssembleOldPojo();
        if (assembleOldModel != null) {
            try {
                if (!StringUtils.isEmpty(assembleOldModel.getOldId())) {
                    assembleOldPojo.setOldId(Long.valueOf(assembleOldModel.getOldId()));
                }
                AssemblePojo assemblePojo = new AssemblePojo();
                if (!StringUtils.isEmpty(assembleOldModel.getAssembleId())) {
                    assemblePojo.setAssembleId(Long.valueOf(assembleOldModel.getAssembleId()));
                }
                ParamPojo pnCode = new ParamPojo();
                pnCode.setParamCode(assembleOldModel.getPnCode());
                assemblePojo.setPnCode(pnCode);
                assemblePojo.setSnCode(assembleOldModel.getSnCode());
                DeliverStatusPojo deliverStatusPojo = new DeliverStatusPojo();
                deliverStatusPojo.setStatusCode(assembleOldModel.getAssembleStatusCode());
                deliverStatusPojo.setStatusName(assembleOldModel.getAssembleStatusName());
                assemblePojo.setAssembleStatus(deliverStatusPojo);
                assemblePojo.setJobNo(assembleOldModel.getJobNo());
                assemblePojo.setAssembleMemo(assembleOldModel.getAssembleMemo());
                if (!StringUtils.isEmpty(assembleOldModel.getCompleteDate())) {
                    assemblePojo.setCompleteDate(DateUtil.StringToTimestamp(assembleOldModel.getCompleteDate(), DateUtil.DATA_DAY));
                }
                assembleOldPojo.setAssemblePojo(assemblePojo);
                assembleOldPojo.setBeginTime(DateUtil.StringToTimestamp(assembleOldModel.getBeginTime(), DateUtil.DATA_DAY));
                assembleOldPojo.setEndTime(DateUtil.StringToTimestamp(assembleOldModel.getEndTime(), DateUtil.DATA_DAY));
                assembleOldPojo.setOldMemo(assembleOldModel.getOldMemo());
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser != null && !StringUtils.isEmpty(loginUser.getUserId())) {
                    assembleOldPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
                }
                if (!StringUtils.isEmpty(assembleOldModel.getOptTime())) {
                    assembleOldPojo.setOptTime(DateUtil.StringToTimestamp(assembleOldModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
                } else {
                    assembleOldPojo.setOptTime(DateUtil.GetSystemDateTime(0));
                }
                assembleOldPojo.setRowCount(assembleOldModel.getRowCount());
                assembleOldPojo.setRowIndex(assembleOldModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将AssembleOldModel转换成AssembleOldPojo失败，信息为" + e.getMessage());
            }
        }
        return assembleOldPojo;
    }

    /**
     * 组装老化处理
     *
     * @param assembleOldModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/operateAssembleOld", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel operateAssembleOld(AssembleOldModel assembleOldModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (assembleOldModel != null) {
            try {
                if (!assembleOldService.canAssembleOldModify(assembleOldModel.getAssembleId())) {
                    resultModel.setResult(false);
                    resultModel.setDetail("不能处理老化记录！");
                    return resultModel;
                }
                String statusCode;
                boolean passFlag;
                if (!StringUtils.isEmpty(assembleOldModel.getOptType())) {
                    if (assembleOldModel.getOptType().equals("1")) {
                        statusCode = DefaultParam.PRODUCT_OLDED;
                        passFlag = true;
                    } else if (assembleOldModel.getOptType().equals("3")) {
                        statusCode = DefaultParam.PRODUCT_TEST_REJECT;
                        passFlag = false;
                    } else if (assembleOldModel.getOptType().equals("4")) {
                        statusCode = DefaultParam.PRODUCT_ASSEMBLE_REJECT;
                        passFlag = false;
                    } else {
                        statusCode = DefaultParam.PRODUCT_OLD;
                        passFlag = false;
                    }
                } else {
                    statusCode = DefaultParam.PRODUCT_OLD;
                    passFlag = false;
                }
                assembleOldModel.setAssembleStatusCode(statusCode);
                AssembleOldPojo assembleOldPojo = convertAssembleOldPojo(assembleOldModel, httpSession);
                assembleOldPojo.setPassFlag(passFlag);
                int intResult = assembleOldService.operateAssembleOld(assembleOldPojo);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("老化处理成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("老化处理失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("老化处理失败！");
                logger.error(LOG + "：老化处理失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 将AssembleOldPojo转换成AssembleOldModel
     *
     * @param assembleOldPojo
     * @return
     */
    public AssembleOldModel convertAssembleOldModel(AssembleOldPojo assembleOldPojo) {
        AssembleOldModel assembleOldModel = new AssembleOldModel();
        if (assembleOldPojo != null) {
            assembleOldModel.setOldId(String.valueOf(assembleOldPojo.getOldId()));
            AssemblePojo assemblePojo = assembleOldPojo.getAssemblePojo();
            if (assemblePojo != null) {
                assembleOldModel.setAssembleId(String.valueOf(assemblePojo.getAssembleId()));
                if (assemblePojo.getPnCode() != null) {
                    assembleOldModel.setPnCode(assemblePojo.getPnCode().getParamCode());
                    assembleOldModel.setPnName(assemblePojo.getPnCode().getParamName());
                }
                assembleOldModel.setSnCode(assemblePojo.getSnCode());
                if (assemblePojo.getAssembleStatus() != null) {
                    assembleOldModel.setAssembleStatusCode(assemblePojo.getAssembleStatus().getStatusCode());
                    assembleOldModel.setAssembleStatusName(assemblePojo.getAssembleStatus().getStatusName());
                }
                assembleOldModel.setJobNo(assemblePojo.getJobNo());
                assembleOldModel.setCruxNo(assemblePojo.getCruxNo());
                assembleOldModel.setAssembleMemo(assemblePojo.getAssembleMemo());
                assembleOldModel.setCompleteDate(DateUtil.TimestampToString(assemblePojo.getCompleteDate(), DateUtil.DATA_DAY));
            }
            AssembleTestPojo assembleTestPojo = assembleTestService.getAssembleTestByAssembleId(String.valueOf(assemblePojo.getAssembleId()));
            if (assembleTestPojo != null) {
                assembleOldModel.setTestBeginTime(DateUtil.TimestampToString(assembleTestPojo.getBeginTime(), DateUtil.DATA_DAY));
                assembleOldModel.setTestEndTime(DateUtil.TimestampToString(assembleTestPojo.getEndTime(), DateUtil.DATA_DAY));
            }
            assembleOldModel.setBeginTime(DateUtil.TimestampToString(assembleOldPojo.getBeginTime(), DateUtil.DATA_DAY));
            assembleOldModel.setEndTime(DateUtil.TimestampToString(assembleOldPojo.getEndTime(), DateUtil.DATA_DAY));
            assembleOldModel.setOldMemo(assembleOldPojo.getOldMemo());
            assembleOldModel.setOptUserId(String.valueOf(assemblePojo.getOptUserId()));
            assembleOldModel.setOptUserName(assemblePojo.getOptUserName());
            assembleOldModel.setOptTime(DateUtil.TimestampToString(assemblePojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return assembleOldModel;
    }

}
