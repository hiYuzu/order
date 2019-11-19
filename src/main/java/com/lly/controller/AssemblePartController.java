package com.lly.controller;

import com.lly.model.AssemblePartModel;
import com.lly.model.ResultListModel;
import com.lly.model.UserModel;
import com.lly.pojo.AssemblePartPojo;
import com.lly.pojo.AssemblePojo;
import com.lly.pojo.ParamPojo;
import com.lly.service.IAssemblePartService;
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

@Controller
@RequestMapping("/AssemblePartController")
public class AssemblePartController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AssemblePartController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AssemblePartController.class);

    @Resource
    private IAssemblePartService assemblePartService;


    /**
     * 获取组装单部件
     *
     * @param assemblePartModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAssemblePart", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AssemblePartModel> getAssemblePart(AssemblePartModel assemblePartModel, HttpSession httpSession) {
        ResultListModel<AssemblePartModel> resultListModel = new ResultListModel<AssemblePartModel>();
        List<AssemblePartModel> assemblePartModelList = new ArrayList<AssemblePartModel>();
        List<AssemblePartPojo> assemblePartPojoList;
        AssemblePartPojo assemblePartPojo = convertAssemblePartPojo(assemblePartModel, httpSession);
        int count = assemblePartService.getAssemblePartCount(assemblePartPojo);
        if (count > 0) {
            assemblePartPojoList = assemblePartService.getAssemblePart(assemblePartPojo);
            for (AssemblePartPojo assemblePartPojoTemp : assemblePartPojoList) {
                AssemblePartModel assemblePartModelTemp = convertAssemblePartModel(assemblePartPojoTemp);
                if (assemblePartModelTemp != null) {
                    assemblePartModelList.add(assemblePartModelTemp);
                }
            }
        }
        resultListModel.setData(assemblePartModelList);
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }


    /**
     * 获取组装单部件（通过组装单ID）
     * @param assembleId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getPartsByAssembleId", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AssemblePartModel> getPartsByAssembleId(String assembleId, HttpSession httpSession) {
        ResultListModel<AssemblePartModel> resultListModel = new ResultListModel<AssemblePartModel>();
        List<AssemblePartModel> assemblePartModelList = new ArrayList<AssemblePartModel>();
        List<AssemblePartPojo> assemblePartPojoList;
        assemblePartPojoList = assemblePartService.getPartsByAssembleId(assembleId);
        for (AssemblePartPojo assemblePartPojoTemp : assemblePartPojoList) {
            AssemblePartModel assemblePartModelTemp = convertAssemblePartModel(assemblePartPojoTemp);
            if (assemblePartModelTemp != null) {
                assemblePartModelList.add(assemblePartModelTemp);
            }
        }

        resultListModel.setData(assemblePartModelList);
        resultListModel.setCount(assemblePartModelList.size());
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 将AssemblePartPojo转换成AssemblePartModel
     *
     * @param assemblePartPojo
     * @return
     */
    public AssemblePartModel convertAssemblePartModel(AssemblePartPojo assemblePartPojo) {
        AssemblePartModel assemblePartModel = new AssemblePartModel();
        if (assemblePartPojo != null) {
            assemblePartModel.setPartId(String.valueOf(assemblePartPojo.getPartId()));
            if (assemblePartPojo.getAssemblePojo() != null) {
                assemblePartModel.setAssembleId(String.valueOf(assemblePartPojo.getAssemblePojo().getAssembleId()));
                assemblePartModel.setSnCode(assemblePartPojo.getAssemblePojo().getSnCode());
            }
            if (assemblePartPojo.getPartType() != null) {
                assemblePartModel.setPartTypeCode(assemblePartPojo.getPartType().getParamCode());
                assemblePartModel.setPartTypeName(assemblePartPojo.getPartType().getParamName());
            }
            assemblePartModel.setPartNo(assemblePartPojo.getPartNo());
            assemblePartModel.setPartMemo(assemblePartPojo.getPartMemo());
            assemblePartModel.setOptUserId(String.valueOf(assemblePartPojo.getOptUserId()));
            assemblePartModel.setOptUserName(assemblePartPojo.getOptUserName());
            assemblePartModel.setOptTime(DateUtil.TimestampToString(assemblePartPojo.getOptTime(), DateUtil.DATA_TIME_SECOND));
        }
        return assemblePartModel;
    }

    /**
     * 将AssemblePartModel转换成AssemblePartPojo
     *
     * @param assemblePartModel
     * @param httpSession
     * @return
     */
    public AssemblePartPojo convertAssemblePartPojo(AssemblePartModel assemblePartModel, HttpSession httpSession) {
        AssemblePartPojo assemblePartPojo = new AssemblePartPojo();
        if (assemblePartModel != null) {
            if (!StringUtils.isEmpty(assemblePartModel.getPartId())) {
                assemblePartPojo.setPartId(Long.valueOf(assemblePartModel.getPartId()));
            }
            AssemblePojo assemblePojo = new AssemblePojo();
            if (!StringUtils.isEmpty(assemblePartModel.getAssembleId())) {
                assemblePojo.setAssembleId(Long.valueOf(assemblePartModel.getAssembleId()));
                assemblePojo.setSnCode(assemblePartModel.getSnCode());
            }
            assemblePartPojo.setAssemblePojo(assemblePojo);
            ParamPojo partType = new ParamPojo();
            partType.setParamCode(assemblePartModel.getPartTypeCode());
            partType.setParamName(assemblePartModel.getPartTypeName());
            assemblePartPojo.setPartType(partType);
            assemblePartPojo.setPartNo(assemblePartModel.getPartNo());
            assemblePartPojo.setPartMemo(assemblePartModel.getPartMemo());
            UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
            if (loginUser != null && !StringUtils.isEmpty(loginUser.getUserId())) {
                assemblePartPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
            }
            if (!StringUtils.isEmpty(assemblePartModel.getOptTime())) {
                assemblePartPojo.setOptTime(DateUtil.StringToTimestamp(assemblePartModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
            } else {
                assemblePartPojo.setOptTime(DateUtil.GetSystemDateTime(0));
            }
            assemblePartPojo.setRowCount(assemblePartModel.getRowCount());
            assemblePartPojo.setRowIndex(assemblePartModel.getRowIndex());
        }
        return assemblePartPojo;
    }

}
