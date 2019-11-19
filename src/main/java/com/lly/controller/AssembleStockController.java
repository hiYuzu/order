package com.lly.controller;

import com.lly.model.AssembleOldModel;
import com.lly.model.ResultListModel;
import com.lly.pojo.*;
import com.lly.service.IAssembleOldService;
import com.lly.util.DefaultParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/AssembleStockController")
public class AssembleStockController {

    @Resource
    private IAssembleOldService assembleOldService;

    @Resource
    private AssembleOldController assembleOldController;


    /**
     * 获取库存信息
     *
     * @param assembleOldModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getAssembleStock", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<AssembleOldModel> getAssembleStock(AssembleOldModel assembleOldModel, HttpSession httpSession) {
        ResultListModel<AssembleOldModel> resultListModel = new ResultListModel<AssembleOldModel>();
        List<AssembleOldModel> assembleOldModelList = new ArrayList<AssembleOldModel>();
        List<AssembleOldPojo> assembleOldPojoList;
        AssembleOldPojo assembleOldPojo = assembleOldController.convertAssembleOldPojo(assembleOldModel, httpSession);
        List<String> statusCodeList = new ArrayList<>();
        if (assembleOldModel.getFinishFlag() != null) {
            if (assembleOldModel.getFinishFlag()) {
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER);//已发货
            } else {
                statusCodeList.add(DefaultParam.PRODUCT_OLDED);//已老化,待发货
                statusCodeList.add(DefaultParam.PRODUCT_DELIVER_RESET);//已重置,待发货
            }
        }else{
            statusCodeList.add(DefaultParam.PRODUCT_DELIVER);//已发货
            statusCodeList.add(DefaultParam.PRODUCT_OLDED);//已老化,待发货
            statusCodeList.add(DefaultParam.PRODUCT_DELIVER_RESET);//已重置,待发货
        }
        int count = assembleOldService.getAssembleOldCount(assembleOldPojo, statusCodeList);
        if (count > 0) {
            assembleOldPojoList = assembleOldService.getAssembleOld(assembleOldPojo, statusCodeList);
            for (AssembleOldPojo temp : assembleOldPojoList) {
                AssembleOldModel assembleOldModelTemp = assembleOldController.convertAssembleOldModel(temp);
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

}
