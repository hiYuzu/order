package com.lly.controller;

import com.lly.model.ParamModel;
import com.lly.model.ResultListModel;
import com.lly.model.ResultModel;
import com.lly.model.UserModel;
import com.lly.pojo.ParamPojo;
import com.lly.pojo.ParamType;
import com.lly.service.IParamService;
import com.lly.util.DateUtil;
import com.lly.util.DefaultParam;
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
 * @Description:
 * @Date: Create in 2018/2/11 14:40
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/ParamController")
public class ParamController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "LoginController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(ParamController.class);

    @Resource
    private IParamService paramService;

    /**
     * 获取系统参数信息数据
     * @param paramModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getParam",method={RequestMethod.POST})
    @ResponseBody
    public ResultListModel<ParamModel> getParam(ParamModel paramModel, HttpSession httpSession){
        ResultListModel<ParamModel> resultListModel = new ResultListModel<ParamModel>();
        List<ParamModel> paramModelList = new ArrayList<ParamModel>();
        List<ParamPojo> paramPojoList;
        ParamPojo paramPojo = convertParamPojo(paramModel,httpSession);
        int count = paramService.getParamCount(paramPojo);
        if (count > 0) {
            paramPojoList = paramService.getParam(paramPojo);
            for (ParamPojo temp : paramPojoList) {
                ParamModel paramModelTemp = convertParamModel(temp);
                if (paramModelTemp != null) {
                    paramModelList.add(paramModelTemp);
                }
            }
            resultListModel.setData(paramModelList);
        }
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 获取系统参数信息数据(通过参数类型)
     * @param typeCode
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getParamByType",method={RequestMethod.POST})
    @ResponseBody
    public ResultListModel<ParamModel> getParamByType(String typeCode, HttpSession httpSession){
        ResultListModel<ParamModel> resultListModel = new ResultListModel<ParamModel>();
        List<ParamModel> paramModelList = new ArrayList<ParamModel>();
        List<ParamPojo> paramPojoList = paramService.getParamByType(typeCode);
        for (ParamPojo temp : paramPojoList) {
            ParamModel paramModelTemp = convertParamModel(temp);
            if (paramModelTemp != null) {
                paramModelList.add(paramModelTemp);
            }
        }
        resultListModel.setData(paramModelList);
        resultListModel.setCount(paramModelList.size());
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }


    /**
     * 新增系统参数
     * @param paramModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/insertParam", method = { RequestMethod.POST })
    @ResponseBody
    public ResultModel insertParam(ParamModel paramModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (paramModel != null) {
            try {
                ParamPojo paramPojo = new ParamPojo();
                paramPojo.setParamCode(paramModel.getParamCode());
                if (paramService.getParamCount(paramPojo) == 0) {
                    paramPojo = convertParamPojo(paramModel,httpSession);
                    int intResult = paramService.insertParam(paramPojo);
                    if (intResult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("新增系统参数成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增系统参数失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此系统参数！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增系统参数失败！");
                logger.error(LOG + "：新增系统参数失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新参数
     * @param paramModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateParam", method = { RequestMethod.POST })
    @ResponseBody
    public ResultModel updateParam(ParamModel paramModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (paramModel != null) {
            try {
                if (paramService.existUpdateParam(paramModel.getParamId(),paramModel.getParamCode()) == 0) {
                    ParamPojo paramPojo = convertParamPojo(paramModel,httpSession);
                    int intResult = paramService.updateParam(paramPojo);
                    if (intResult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("更新系统参数成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新系统参数失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此系统参数！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新系统参数失败！");
                logger.error(LOG + "：更新系统参数失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }


    /**
     * 删除参数
     * @param list
     * @return
     */
    @RequestMapping(value = "/deleteParams", method = { RequestMethod.POST })
    public @ResponseBody ResultModel deleteParams(@RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                if(paramService.existDeleteParam(list)>0)
                {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已使用，不能删除！");
                    return resultModel;
                }else{
                    int intResult = paramService.deleteParam(list);
                    if (intResult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("删除参数成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("删除参数失败！");
                    }
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除参数失败！");
                logger.error(LOG + "：删除参数失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除参数失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * 将ParamPojo转换为ParamModel
     * @param paramPojo
     * @return
     */
    public ParamModel convertParamModel(ParamPojo paramPojo){
        ParamModel paramModel = new ParamModel();
        if(paramPojo != null){
            paramModel.setParamId(String.valueOf(paramPojo.getParamId()));
            if(paramPojo.getParamType() != null){
                paramModel.setParamTypeCode(paramPojo.getParamType().getTypeCode());
                paramModel.setParamTypeName(paramPojo.getParamType().getTypeName());
            }
            paramModel.setParamCode(paramPojo.getParamCode());
            paramModel.setParamName(paramPojo.getParamName());
            paramModel.setOptUserId(String.valueOf(paramPojo.getOptUserId()));
            paramModel.setOptUserName(paramPojo.getOptUserName());
            paramModel.setOptTime(DateUtil.TimestampToString(paramPojo.getOptTime(),DateUtil.DATA_TIME_SECOND));
        }
        return paramModel;
    }

    /**
     * 将ParamModel转换为ParamPojo
     * @param paramModel
     * @param httpSession
     * @return
     */
    public ParamPojo convertParamPojo(ParamModel paramModel, HttpSession httpSession){
        ParamPojo paramPojo = new ParamPojo();
        if(paramModel != null) {
            try {
                if (paramModel.getParamId() != null && !paramModel.getParamId().isEmpty()) {
                    paramPojo.setParamId(Integer.valueOf(paramModel.getParamId()));
                }
                ParamType paramType = new ParamType();
                paramType.setTypeCode(paramModel.getParamTypeCode());
                paramType.setTypeName(paramModel.getParamTypeName());
                paramPojo.setParamType(paramType);
                paramPojo.setParamCode(paramModel.getParamCode());
                paramPojo.setParamName(paramModel.getParamName());
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
                    paramPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
                }
                if(paramModel.getOptTime() != null && !paramModel.getOptTime().isEmpty()){
                    paramPojo.setOptTime(DateUtil.StringToTimestamp(paramModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
                }else{
                    paramPojo.setOptTime(DateUtil.GetSystemDateTime(0));
                }
                paramPojo.setRowCount(paramModel.getRowCount());
                paramPojo.setRowIndex(paramModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将ParamModel转换成ParamPojo失败，信息为" + e.getMessage());
            }
        }
        return paramPojo;
    }

}
