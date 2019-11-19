package com.tcb.controller;

import com.tcb.model.AuthoritySetModel;
import com.tcb.model.ResultModel;
import com.tcb.model.UserModel;
import com.tcb.pojo.AuthoritySetPojo;
import com.tcb.pojo.PagePojo;
import com.tcb.service.IAuthoritySetService;
import com.tcb.service.IUserService;
import com.tcb.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangLei
 * @Description: 权限设置控制器
 * @Date: Create in 2018/3/8 16:11
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/AuthoritySetController")
public class AuthoritySetController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "AuthoritySetController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(AuthoritySetController.class);

    @Resource
    private IAuthoritySetService authoritySetService;

    /**
     * 判断是否有此页面权限
     * @param pageCode
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/isAuthorityPage", method = {RequestMethod.POST})
    @ResponseBody
    private ResultModel isAuthorityPage(String pageCode,HttpSession httpSession){
        ResultModel resultModel = new ResultModel();
        UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
        if(loginUser != null){
            String userType = loginUser.getUserType();
            if(authoritySetService.haveAuthority(userType,pageCode)>0){
                resultModel.setResult(true);
            }else{
                resultModel.setResult(false);
                resultModel.setDetail("无此页面权限，请更换登录用户！");
            }
        }
        return resultModel;
    }

    /**
     * 设置权限页面
     * @param authoritySetModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/operateAuthoritySet", method = {RequestMethod.POST})
    @ResponseBody
    private ResultModel operateAuthoritySet(AuthoritySetModel authoritySetModel, HttpSession httpSession){
        ResultModel resultModel = new ResultModel();
        try {
            if (authoritySetModel != null) {
                Map<String, List<AuthoritySetPojo>> authoritySetPojoMap = new HashMap<String, List<AuthoritySetPojo>>();
                authoritySetPojoMap.put("1", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("2", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("3", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("4", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("5", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("6", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("7", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("8", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("9", new ArrayList<AuthoritySetPojo>());
                authoritySetPojoMap.put("10", new ArrayList<AuthoritySetPojo>());
                //assemble
                if ("on".equals(authoritySetModel.getAssemble1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAssemble10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getAssembleName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //test
                if ("on".equals(authoritySetModel.getTest1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getTest10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getTestName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //old
                if ("on".equals(authoritySetModel.getOld1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOld10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getOldName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //stock
                if ("on".equals(authoritySetModel.getStock1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getStock10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getStockName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //authority
                if ("on".equals(authoritySetModel.getAuthority1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getAuthority10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getAuthorityName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //business
                if ("on".equals(authoritySetModel.getBusiness1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getBusiness10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getBusinessName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //deliver
                if ("on".equals(authoritySetModel.getDeliver1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getDeliver10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getDeliverName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //install
                if ("on".equals(authoritySetModel.getInstall1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getInstall10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getInstallName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //param
                if ("on".equals(authoritySetModel.getParam1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getParam10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getParamName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //repair
                if ("on".equals(authoritySetModel.getRepair1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getRepair10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getRepairName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //user
                if ("on".equals(authoritySetModel.getUser1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getUser10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getUserName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //news
                if ("on".equals(authoritySetModel.getNews1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getNews10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getNewsName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                //outsourcing
                if ("on".equals(authoritySetModel.getOutsourcing1())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("1", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("1").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing2())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("2", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("2").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing3())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("3", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("3").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing4())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("4", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("4").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing5())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("5", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("5").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing6())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("6", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("6").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing7())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("7", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("7").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing8())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("8", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("8").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing9())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("9", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("9").add(authoritySetPojo);
                }
                if ("on".equals(authoritySetModel.getOutsourcing10())) {
                    AuthoritySetPojo authoritySetPojo = convertAuthoritySetPojo("10", authoritySetModel.getOutsourcingName(), httpSession);
                    authoritySetPojoMap.get("10").add(authoritySetPojo);
                }
                int intResult = authoritySetService.operateAuthoritySet(authoritySetPojoMap);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("设置权限页面成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("设置权限页面失败！");
                }
            }else{
                resultModel.setResult(false);
                resultModel.setDetail("没有可以操作的数据！");
            }
        }catch (Exception e){

            resultModel.setResult(false);
            resultModel.setDetail("设置权限页面失败！");
            logger.error(LOG + "：设置权限页面失败，信息为：" + e.getMessage());
        }
        return resultModel;
    }

    /**
     * 转换成AuthoritySetPojo
     * @param userType
     * @param pageName
     * @param httpSession
     * @return
     */
    private AuthoritySetPojo convertAuthoritySetPojo(String userType,String pageName,HttpSession httpSession){
        String authorityFlag = "1";
        AuthoritySetPojo authoritySetPojo = new AuthoritySetPojo();
        authoritySetPojo.setUserType(userType);
        authoritySetPojo.setAuthorityFlag(authorityFlag);
        PagePojo pagePojo = new PagePojo();
        pagePojo.setPageCode(pageName);
        authoritySetPojo.setPagePojo(pagePojo);
        UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
        if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
            authoritySetPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
        }
        authoritySetPojo.setOptTime(DateUtil.GetSystemDateTime(0));
        return authoritySetPojo;
    }


}
