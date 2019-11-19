package com.lly.controller;

import com.lly.model.DeliverStatusModel;
import com.lly.model.ResultListModel;
import com.lly.pojo.DeliverStatusPojo;
import com.lly.service.IDeliverStatusService;
import com.lly.util.DefaultParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.expression.Lists;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 发货单状态控制器
 * @Date: Create in 2019/1/24 10:37
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/DeliverStatusController")
public class DeliverStatusController {

    @Resource
    private IDeliverStatusService deliverStatusService;

    @RequestMapping(value = "/getDeliverStatus", method = {RequestMethod.POST})
    @ResponseBody
    public ResultListModel<DeliverStatusModel> getDeliverStatus(DeliverStatusModel deliverMode, HttpSession httpSession) {
        ResultListModel<DeliverStatusModel> resultListModel = new ResultListModel<>();
        List<DeliverStatusModel> deliverStatusModelList = new ArrayList<>();
        List<DeliverStatusPojo> deliverStatusPojoList = deliverStatusService.getDeliverStatus();
        if (deliverStatusPojoList != null && deliverStatusPojoList.size() > 0) {
            for (DeliverStatusPojo temp : deliverStatusPojoList) {
                DeliverStatusModel deliverStatusModel = convertDeliverStatusModel(temp);
                if (deliverStatusModel != null) {
                    deliverStatusModelList.add(deliverStatusModel);
                }
            }
        }
        resultListModel.setData(deliverStatusModelList);
        resultListModel.setCount(deliverStatusModelList.size());
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 将DeliverStatusPojo转换为DeliverStatusModel
     *
     * @param deliverStatusPojo
     * @return
     */
    private DeliverStatusModel convertDeliverStatusModel(DeliverStatusPojo deliverStatusPojo) {
        DeliverStatusModel deliverStatusModel = new DeliverStatusModel();
        if (deliverStatusPojo != null) {
            deliverStatusModel.setStatusId(String.valueOf(deliverStatusPojo.getStatusId()));
            deliverStatusModel.setStatusCode(deliverStatusPojo.getStatusCode());
            deliverStatusModel.setStatusName(deliverStatusPojo.getStatusName());
            deliverStatusModel.setStatusRemark(deliverStatusPojo.getStatusRemark());
        }
        return deliverStatusModel;
    }

}
