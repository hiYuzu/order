package com.lly.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lly.model.*;
import com.lly.pojo.*;
import com.lly.service.IAssembleTestService;
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
 * @Description: 组装测试控制器
 * @Date: Create in 2019/3/25 14:16
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/AssembleTestController")
public class AssembleTestController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AssembleTestController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AssembleTestController.class);

    @Resource
    private IAssembleTestService assembleTestService;

    @Resource
    private AssemblePartController assemblePartController;

    /**
     * 获取组装测试信息
     *
     * @param assembleTestModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAssembleTest", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AssembleTestModel> getAssembleTest(AssembleTestModel assembleTestModel, HttpSession httpSession) {
        ResultListModel<AssembleTestModel> resultListModel = new ResultListModel<AssembleTestModel>();
        List<AssembleTestModel> assembleTestModelList = new ArrayList<AssembleTestModel>();
        List<AssembleTestPojo> assembleTestPojoList;
        AssembleTestPojo assembleTestPojo = convertAssembleTestPojo(assembleTestModel, httpSession);
        List<String> statusCodeList = new ArrayList<>();
        if (assembleTestModel.getFinishFlag() != null) {
            if (assembleTestModel.getFinishFlag()) {
                statusCodeList.add(DefaultParam.PRODUCT_TESTED);//已测试,待老化
                statusCodeList.add(DefaultParam.PRODUCT_OLD);//老化中
                statusCodeList.add(DefaultParam.PRODUCT_OLDED);//已老化,待发货
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER);//待发货
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER_RESET);//已重置,待发货
            } else {
                statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLED);//已组装，待测试
                statusCodeList.add(DefaultParam.PRODUCT_TEST);//测试中
                statusCodeList.add(DefaultParam.PRODUCT_TEST_REJECT);//被驳回,待测试
            }
        } else {
            statusCodeList.add(DefaultParam.PRODUCT_TESTED);//已测试,待老化
            statusCodeList.add(DefaultParam.PRODUCT_OLD);//老化中
            statusCodeList.add(DefaultParam.PRODUCT_OLDED);//已老化,待发货
            statusCodeList.add(DefaultParam.PRODUCT_DELIVER);//待发货
            statusCodeList.add(DefaultParam.PRODUCT_DELIVER_RESET);//已重置,待发货
            statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLED);//已组装，待测试
            statusCodeList.add(DefaultParam.PRODUCT_TEST);//测试中
            statusCodeList.add(DefaultParam.PRODUCT_TEST_REJECT);//被驳回,待测试
        }
        int count = assembleTestService.getAssembleTestCount(assembleTestPojo, statusCodeList);
        if (count > 0) {
            assembleTestPojoList = assembleTestService.getAssembleTest(assembleTestPojo, statusCodeList);
            for (AssembleTestPojo temp : assembleTestPojoList) {
                AssembleTestModel assembleTestModelTemp = convertAssembleTestModel(temp);
                if (assembleTestModelTemp != null) {
                    assembleTestModelList.add(assembleTestModelTemp);
                }
            }
            resultListModel.setData(assembleTestModelList);
        }
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 组装测试处理
     *
     * @param assembleTestModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/operateAssembleTest", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel operateAssembleTest(AssembleTestModel assembleTestModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (assembleTestModel != null) {
            try {
                if (!assembleTestService.canAssembleTestModify(assembleTestModel.getAssembleId())) {
                    resultModel.setResult(false);
                    resultModel.setDetail("不能处理测试记录！");
                    return resultModel;
                }
                String statusCode;
                boolean passFlag;
                if (!StringUtils.isEmpty(assembleTestModel.getOptType())) {
                    if (assembleTestModel.getOptType().equals("1")) {
                        statusCode = DefaultParam.PRODUCT_TESTED;
                        passFlag = true;
                    } else if (assembleTestModel.getOptType().equals("0")) {
                        statusCode = DefaultParam.PRODUCT_ASSEMBLE_REJECT;
                        passFlag = false;
                    } else {
                        statusCode = DefaultParam.PRODUCT_TEST;
                        passFlag = false;
                    }
                } else {
                    statusCode = DefaultParam.PRODUCT_TEST;
                    passFlag = false;
                }
                assembleTestModel.setAssembleStatusCode(statusCode);
                AssembleTestPojo assembleTestPojo = convertAssembleTestPojo(assembleTestModel, httpSession);
                assembleTestPojo.setPassFlag(passFlag);
                List<AssemblePartPojo> assemblePartPojoList = new ArrayList<AssemblePartPojo>();
                if (!StringUtils.isEmpty(assembleTestModel.getPartsModelString())) {
                    Gson gson = new Gson();
                    List<AssemblePartModel> assemblePartModelList = gson.fromJson(assembleTestModel.getPartsModelString(),
                            new TypeToken<List<AssemblePartModel>>() {
                            }.getType());
                    for (AssemblePartModel temp : assemblePartModelList) {
                        if (temp != null) {
                            AssemblePartPojo assemblePartPojo = assemblePartController.convertAssemblePartPojo(temp, httpSession);
                            if (assemblePartPojo != null) {
                                assemblePartPojoList.add(assemblePartPojo);
                            }
                        }
                    }
                }
                int intResult = assembleTestService.operateAssembleTest(assembleTestPojo, assemblePartPojoList, assembleTestModel.getCruxNo());
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("测试处理成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("测试处理失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("测试处理失败！");
                logger.error(LOG + "：测试处理失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 将AssembleTestModel转换成AssembleTestPojo
     *
     * @param assembleTestModel
     * @param httpSession
     * @return
     */
    public AssembleTestPojo convertAssembleTestPojo(AssembleTestModel assembleTestModel, HttpSession httpSession) {
        AssembleTestPojo assembleTestPojo = new AssembleTestPojo();
        if (assembleTestModel != null) {
            try {
                if (!StringUtils.isEmpty(assembleTestModel.getTestId())) {
                    assembleTestPojo.setTestId(Long.valueOf(assembleTestModel.getTestId()));
                }
                AssemblePojo assemblePojo = new AssemblePojo();
                if (!StringUtils.isEmpty(assembleTestModel.getAssembleId())) {
                    assemblePojo.setAssembleId(Long.valueOf(assembleTestModel.getAssembleId()));
                }
                ParamPojo pnCode = new ParamPojo();
                pnCode.setParamCode(assembleTestModel.getPnCode());
                assemblePojo.setPnCode(pnCode);
                assemblePojo.setSnCode(assembleTestModel.getSnCode());
                DeliverStatusPojo deliverStatusPojo = new DeliverStatusPojo();
                deliverStatusPojo.setStatusCode(assembleTestModel.getAssembleStatusCode());
                deliverStatusPojo.setStatusName(assembleTestModel.getAssembleStatusName());
                assemblePojo.setAssembleStatus(deliverStatusPojo);
                assemblePojo.setJobNo(assembleTestModel.getJobNo());
                assemblePojo.setCruxNo(assembleTestModel.getCruxNo());
                assemblePojo.setAssembleMemo(assembleTestModel.getAssembleMemo());
                if (!StringUtils.isEmpty(assembleTestModel.getCompleteDate())) {
                    assemblePojo.setCompleteDate(DateUtil.StringToTimestamp(assembleTestModel.getCompleteDate(), DateUtil.DATA_DAY));
                }
                assembleTestPojo.setAssemblePojo(assemblePojo);
                assembleTestPojo.setBeginTime(DateUtil.StringToTimestamp(assembleTestModel.getBeginTime(), DateUtil.DATA_DAY));
                assembleTestPojo.setEndTime(DateUtil.StringToTimestamp(assembleTestModel.getEndTime(), DateUtil.DATA_DAY));
                assembleTestPojo.setTestMemo(assembleTestModel.getTestMemo());
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser != null && !StringUtils.isEmpty(loginUser.getUserId())) {
                    assembleTestPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
                }
                if (!StringUtils.isEmpty(assembleTestModel.getOptTime())) {
                    assembleTestPojo.setOptTime(DateUtil.StringToTimestamp(assembleTestModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
                } else {
                    assembleTestPojo.setOptTime(DateUtil.GetSystemDateTime(0));
                }
                assembleTestPojo.setRowCount(assembleTestModel.getRowCount());
                assembleTestPojo.setRowIndex(assembleTestModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将AssembleTestModel转换成AssembleTestPojo失败，信息为" + e.getMessage());
            }
        }
        return assembleTestPojo;
    }


    /**
     * 将AssembleTestPojo转换成AssembleTestModel
     *
     * @param assembleTestPojo
     * @return
     */
    public AssembleTestModel convertAssembleTestModel(AssembleTestPojo assembleTestPojo) {
        AssembleTestModel assembleTestModel = new AssembleTestModel();
        if (assembleTestPojo != null) {
            assembleTestModel.setTestId(String.valueOf(assembleTestPojo.getTestId()));
            AssemblePojo assemblePojo = assembleTestPojo.getAssemblePojo();
            if (assemblePojo != null) {
                assembleTestModel.setAssembleId(String.valueOf(assemblePojo.getAssembleId()));
                if (assemblePojo.getPnCode() != null) {
                    assembleTestModel.setPnCode(assemblePojo.getPnCode().getParamCode());
                    assembleTestModel.setPnName(assemblePojo.getPnCode().getParamName());
                }
                assembleTestModel.setSnCode(assemblePojo.getSnCode());
                if (assemblePojo.getAssembleStatus() != null) {
                    assembleTestModel.setAssembleStatusCode(assemblePojo.getAssembleStatus().getStatusCode());
                    assembleTestModel.setAssembleStatusName(assemblePojo.getAssembleStatus().getStatusName());
                }
                assembleTestModel.setJobNo(assemblePojo.getJobNo());
                assembleTestModel.setCruxNo(assemblePojo.getCruxNo());
                assembleTestModel.setAssembleMemo(assemblePojo.getAssembleMemo());
                assembleTestModel.setCompleteDate(DateUtil.TimestampToString(assemblePojo.getCompleteDate(), DateUtil.DATA_DAY));
            }
            assembleTestModel.setBeginTime(DateUtil.TimestampToString(assembleTestPojo.getBeginTime(), DateUtil.DATA_DAY));
            assembleTestModel.setEndTime(DateUtil.TimestampToString(assembleTestPojo.getEndTime(), DateUtil.DATA_DAY));
            assembleTestModel.setTestMemo(assembleTestPojo.getTestMemo());
            assembleTestModel.setOptUserId(String.valueOf(assemblePojo.getOptUserId()));
            assembleTestModel.setOptUserName(assemblePojo.getOptUserName());
            assembleTestModel.setOptTime(DateUtil.TimestampToString(assemblePojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return assembleTestModel;
    }

}
