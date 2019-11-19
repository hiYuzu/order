package com.tcb.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tcb.model.*;
import com.tcb.pojo.*;
import com.tcb.service.IAssembleOldService;
import com.tcb.service.IAssembleService;
import com.tcb.service.IAssembleTestService;
import com.tcb.util.DateUtil;
import com.tcb.util.DefaultParam;
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
 * @Description: 组装控制器
 * @Date: Create in 2019/3/19 9:12
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/AssembleController")
public class AssembleController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AssembleController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AssembleController.class);

    @Resource
    private IAssembleService assembleService;

    @Resource
    private IAssembleTestService assembleTestService;

    @Resource
    private IAssembleOldService assembleOldService;

    @Resource
    private AssemblePartController assemblePartController;

    /**
     * 获取待处理单据数量
     *
     * @param assembleType
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAssembleCount", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public int getAssembleCount(String assembleType, HttpSession httpSession) {
        int result = 0;
        if (assembleType.equals("assemble")) {
            AssemblePojo assemblePojo = new AssemblePojo();
            List<String> statusCodeList = new ArrayList<>();
            statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLE);//组装中
            statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLE_REJECT);//被驳回,待组装
            result = assembleService.getAssembleCount(assemblePojo, statusCodeList);
        } else if (assembleType.equals("test")) {
            AssembleTestPojo assembleTestPojo = new AssembleTestPojo();
            List<String> statusCodeList = new ArrayList<>();
            statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLED);//已组装，待测试
            statusCodeList.add(DefaultParam.PRODUCT_TEST);//测试中
            statusCodeList.add(DefaultParam.PRODUCT_TEST_REJECT);//被驳回,待测试
            result = assembleTestService.getAssembleTestCount(assembleTestPojo, statusCodeList);
        } else if (assembleType.equals("old")) {
            AssembleOldPojo assembleOldPojo = new AssembleOldPojo();
            List<String> statusCodeList = new ArrayList<>();
            statusCodeList.add(DefaultParam.PRODUCT_TESTED);//已测试,待老化
            statusCodeList.add(DefaultParam.PRODUCT_OLD);//老化中
            result = assembleOldService.getAssembleOldCount(assembleOldPojo, statusCodeList);
        }
        return result;
    }

    /**
     * 获取组装单信息
     *
     * @param assembleModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAssemble", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AssembleModel> getAssemble(AssembleModel assembleModel, HttpSession httpSession) {
        ResultListModel<AssembleModel> resultListModel = new ResultListModel<AssembleModel>();
        List<AssembleModel> assembleModelList = new ArrayList<AssembleModel>();
        List<AssemblePojo> assemblePojoList;
        AssemblePojo assemblePojo = convertAssemblePojo(assembleModel, httpSession);
        List<String> statusCodeList = new ArrayList<>();
        if (assembleModel.getFinishFlag() != null) {
            if (assembleModel.getFinishFlag()) {
                statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLED);//已组装，待测试
                statusCodeList.add(DefaultParam.PRODUCT_TEST);//测试中
                statusCodeList.add(DefaultParam.PRODUCT_TESTED);//已测试,待老化
                statusCodeList.add(DefaultParam.PRODUCT_TEST_REJECT);//被驳回,待测试
                statusCodeList.add(DefaultParam.PRODUCT_OLD);//老化中
                statusCodeList.add(DefaultParam.PRODUCT_OLDED);//已老化,待发货
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER);//待发货
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER_RESET);//已重置,待发货
            } else {
                statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLE);//组装中
                statusCodeList.add(DefaultParam.PRODUCT_ASSEMBLE_REJECT);//被驳回,待组装
            }
        }
        int count = assembleService.getAssembleCount(assemblePojo, statusCodeList);
        if (count > 0) {
            assemblePojoList = assembleService.getAssemble(assemblePojo, statusCodeList);
            for (AssemblePojo temp : assemblePojoList) {
                AssembleModel assembleModelTemp = convertAssembleModel(temp);
                if (assembleModelTemp != null) {
                    assembleModelList.add(assembleModelTemp);
                }
            }
            resultListModel.setData(assembleModelList);
        }
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }


    /**
     * 新增组装单
     *
     * @param assembleModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/insertAssemble", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel insertAssemble(AssembleModel assembleModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (assembleModel != null) {
            try {
                if (!StringUtils.isEmpty(assembleModel.getOptType()) && assembleModel.getOptType().equals(DefaultParam.YES)) {
                    assembleModel.setAssembleStatusCode(DefaultParam.PRODUCT_ASSEMBLED);
                    assembleModel.setAssembleStatusName("已组装，待测试");
                } else {
                    assembleModel.setAssembleStatusCode(DefaultParam.PRODUCT_ASSEMBLE);
                    assembleModel.setAssembleStatusName("组装中");
                }
                AssemblePojo assemblePojo = convertAssemblePojo(assembleModel, httpSession);
                List<AssemblePartPojo> assemblePartPojoList = new ArrayList<AssemblePartPojo>();
                if (!StringUtils.isEmpty(assembleModel.getPartsModelString())) {
                    Gson gson = new Gson();
                    List<AssemblePartModel> assemblePartModelList = gson.fromJson(assembleModel.getPartsModelString(),
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
                int intResult = assembleService.insertAssemble(assemblePojo, assemblePartPojoList);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("新增组装单成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("新增组装单失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增组装单失败！");
                logger.error(LOG + "：新增组装单失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新组装单
     *
     * @param assembleModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateAssemble", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel updateAssemble(AssembleModel assembleModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (assembleModel != null) {
            try {
                if (!StringUtils.isEmpty(assembleModel.getOptType()) && assembleModel.getOptType().equals(DefaultParam.YES)) {
                    assembleModel.setAssembleStatusCode(DefaultParam.PRODUCT_ASSEMBLED);
                    assembleModel.setAssembleStatusName("已组装，待测试");
                } else {
                    assembleModel.setAssembleStatusCode(DefaultParam.PRODUCT_ASSEMBLE);
                    assembleModel.setAssembleStatusName("组装中");
                }
                if (!assembleService.canAssembleModify(assembleModel.getAssembleId())) {
                    resultModel.setResult(false);
                    resultModel.setDetail("不能更新组装单！");
                    return resultModel;
                }
                AssemblePojo assemblePojo = convertAssemblePojo(assembleModel, httpSession);
                List<AssemblePartPojo> assemblePartPojoList = new ArrayList<AssemblePartPojo>();
                if (!StringUtils.isEmpty(assembleModel.getPartsModelString())) {
                    Gson gson = new Gson();
                    List<AssemblePartModel> assemblePartModelList = gson.fromJson(assembleModel.getPartsModelString(),
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
                int intResult = assembleService.updateAssemble(assemblePojo, assemblePartPojoList, false);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("更新组装单成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新组装单失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新组装单失败！");
                logger.error(LOG + "：更新组装单失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }


    /**
     * 删除组装单
     *
     * @param list
     * @return
     */
    @RequestMapping(value = "/deleteAssembles", method = {RequestMethod.POST})
    @ResponseBody
    public ResultModel deleteAssembles(@RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                for (String temp : list) {
                    if (!assembleService.canAssembleDelete(temp)) {
                        resultModel.setResult(false);
                        resultModel.setDetail("不能删除组装单！");
                        return resultModel;
                    }
                }
                int intResult = assembleService.deleteAssembles(list);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("删除组装单成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除组装单失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除组装单失败！");
                logger.error(LOG + "：删除组装单失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除发货单失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * 将AssemblePojo转换成AssembleModel
     *
     * @param assemblePojo
     * @return
     */
    public AssembleModel convertAssembleModel(AssemblePojo assemblePojo) {
        AssembleModel assembleModel = new AssembleModel();
        if (assemblePojo != null) {
            assembleModel.setAssembleId(String.valueOf(assemblePojo.getAssembleId()));
            if (assemblePojo.getPnCode() != null) {
                assembleModel.setPnCode(assemblePojo.getPnCode().getParamCode());
                assembleModel.setPnName(assemblePojo.getPnCode().getParamName());
            }
            assembleModel.setSnCode(assemblePojo.getSnCode());
            if (assemblePojo.getAssembleStatus() != null) {
                assembleModel.setAssembleStatusCode(assemblePojo.getAssembleStatus().getStatusCode());
                assembleModel.setAssembleStatusName(assemblePojo.getAssembleStatus().getStatusName());
            }
            assembleModel.setJobNo(assemblePojo.getJobNo());
            assembleModel.setCruxNo(assemblePojo.getCruxNo());
            assembleModel.setAssembleMemo(assemblePojo.getAssembleMemo());
            assembleModel.setCompleteDate(DateUtil.TimestampToString(assemblePojo.getCompleteDate(), DateUtil.DATA_DAY));
            assembleModel.setOptUserId(String.valueOf(assemblePojo.getOptUserId()));
            assembleModel.setOptUserName(assemblePojo.getOptUserName());
            assembleModel.setOptTime(DateUtil.TimestampToString(assemblePojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return assembleModel;
    }

    /**
     * 将AssembleModel转换成AssemblePojo
     *
     * @param assembleModel
     * @param httpSession
     * @return
     */
    public AssemblePojo convertAssemblePojo(AssembleModel assembleModel, HttpSession httpSession) {
        AssemblePojo assemblePojo = new AssemblePojo();
        if (assembleModel != null) {
            try {
                if (!StringUtils.isEmpty(assembleModel.getAssembleId())) {
                    assemblePojo.setAssembleId(Long.valueOf(assembleModel.getAssembleId()));
                }
                ParamPojo pnCode = new ParamPojo();
                pnCode.setParamCode(assembleModel.getPnCode());
                assemblePojo.setPnCode(pnCode);
                assemblePojo.setSnCode(assembleModel.getSnCode());
                DeliverStatusPojo deliverStatusPojo = new DeliverStatusPojo();
                deliverStatusPojo.setStatusCode(assembleModel.getAssembleStatusCode());
                deliverStatusPojo.setStatusName(assembleModel.getAssembleStatusName());
                assemblePojo.setAssembleStatus(deliverStatusPojo);
                assemblePojo.setJobNo(assembleModel.getJobNo());
                assemblePojo.setCruxNo(assembleModel.getCruxNo());
                assemblePojo.setAssembleMemo(assembleModel.getAssembleMemo());
                if (!StringUtils.isEmpty(assembleModel.getCompleteDate())) {
                    assemblePojo.setCompleteDate(DateUtil.StringToTimestamp(assembleModel.getCompleteDate(), DateUtil.DATA_DAY));
                }
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser != null && !StringUtils.isEmpty(loginUser.getUserId())) {
                    assemblePojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
                }
                if (!StringUtils.isEmpty(assembleModel.getOptTime())) {
                    assemblePojo.setOptTime(DateUtil.StringToTimestamp(assembleModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
                } else {
                    assemblePojo.setOptTime(DateUtil.GetSystemDateTime(0));
                }
                assemblePojo.setRowCount(assembleModel.getRowCount());
                assemblePojo.setRowIndex(assembleModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将AssembleModel转换成AssemblePojo失败，信息为" + e.getMessage());
            }
        }
        return assemblePojo;
    }


}
