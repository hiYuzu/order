package com.lly.controller;

import com.lly.config.SystemConfig;
import com.lly.model.ResultListModel;
import com.lly.model.ResultModel;
import com.lly.model.UserModel;
import com.lly.pojo.UserPojo;
import com.lly.service.IUserService;
import com.lly.util.DateUtil;
import com.lly.util.DefaultParam;
import com.lly.util.ShaUtil;
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
 * @Date: Create in 2018/1/25 14:10
 * @Modify by WangLei
 */
@Controller
@RequestMapping("/UserController")
public class UserController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "UserController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    @Resource
    private SystemConfig systemConfig;

    /**
     * 获取登录用户信息
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getLoginUser",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public UserModel getLoginUser(HttpSession httpSession){
        return (UserModel) httpSession.getAttribute(httpSession.getId());
    }

    /**
     * 获取用户信息数据
     * @param userModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/getUser",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResultListModel<UserModel> getUser(UserModel userModel, HttpSession httpSession){
        ResultListModel<UserModel> resultListModel = new ResultListModel<UserModel>();
        List<UserModel> userModelList = new ArrayList<UserModel>();
        List<UserPojo> userList;
        UserPojo userPojo = convertUserPojo(userModel,true, httpSession);
        int count = userService.getUserCount(userPojo);
        if (count > 0) {
            userList = userService.getUser(userPojo);
            for (UserPojo temp : userList) {
                UserModel userModelTemp = convertUserModel(temp);
                if (userModelTemp != null) {
                    userModelList.add(userModelTemp);
                }
            }
            resultListModel.setData(userModelList);
        }
        resultListModel.setCount(count);
        resultListModel.setCode(DefaultParam.liSelectOk);
        return resultListModel;
    }

    /**
     * 解锁盐城
     * @param passWord
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/unlockScreen",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResultModel unlockScreen(String passWord,HttpSession httpSession){
        ResultModel resultModel = new ResultModel();
        try {
            String unlockPwd = ShaUtil.shaEncode(passWord);
            UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
            if (loginUser == null) {
                resultModel.setResult(false);
                resultModel.setDetail("未登录或超时，请重新登录！");
            } else if (!loginUser.getUserPassword().equals(unlockPwd)) {
                resultModel.setResult(false);
                resultModel.setDetail("密码不正确！");
            }else{
                resultModel.setResult(true);
                resultModel.setDetail("验证成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModel;
    }

    /**
     * 新增用户
     * @param userModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/insertUser", method = { RequestMethod.POST })
    public @ResponseBody ResultModel insertUser(UserModel userModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (userModel != null) {
            try {
                UserPojo userPojo = new UserPojo();
                userPojo.setUserCode(userModel.getUserCode());
                if (userService.getUserCount(userPojo) == 0) {
                    userModel.setUserPassword(DefaultParam.PWD_DEFAULT);
                    userPojo = convertUserPojo(userModel, true,httpSession);
                    int intResult = userService.insertUser(userPojo);
                    if (intResult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("新增用户成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增用户失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此用户，请使用其他注册账号！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增用户失败！");
                logger.error(LOG + "：新增用户失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新用户
     * @param userModel
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateUser", method = { RequestMethod.POST })
    public @ResponseBody ResultModel updateUser(UserModel userModel, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (userModel != null) {
            try {
                if (userService.existUpdateUser(userModel.getUserId(),userModel.getUserCode()) == 0) {
                    userModel.setUserPassword(DefaultParam.PWD_DEFAULT);
                    UserPojo userPojo = convertUserPojo(userModel, false,httpSession);
                    int intResult = userService.updateUser(userPojo);
                    if (intResult > 0) {
                        resultModel.setResult(true);
                        resultModel.setDetail("更新用户成功！");
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新用户失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此用户编码，请更换！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新用户失败！");
                logger.error(LOG + "：更新用户失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("没有可以操作的数据！");
        }
        return resultModel;
    }

    /**
     * 更新用户密码
     * @param oldPwd
     * @param newPwd
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "/updateUserPassword", method = { RequestMethod.POST })
    public @ResponseBody ResultModel updateUserPassword(String oldPwd,String newPwd, HttpSession httpSession) {
        ResultModel resultModel = new ResultModel();
        if (!newPwd.isEmpty() && !oldPwd.isEmpty()) {
            try {
                newPwd = ShaUtil.shaEncode(newPwd);
                oldPwd = ShaUtil.shaEncode(oldPwd);
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser == null) {
                    resultModel.setResult(false);
                    resultModel.setDetail("未登录，不能进行密码修改！");
                } else if (!loginUser.getUserPassword().equals(oldPwd)) {
                    resultModel.setResult(false);
                    resultModel.setDetail("密码不正确！");
                } else if (loginUser.getUserPassword().equals(newPwd)) {
                    resultModel.setResult(false);
                    resultModel.setDetail("新密码与原密码一致！");
                } else {
                    int intResult = userService.updateUserPassword(loginUser.getUserId(), newPwd);
                    if (intResult > 0) {
                        ((UserModel) httpSession.getAttribute(httpSession.getId())).setUserPassword(newPwd);
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("更新用户失败！");
                    }
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新用户失败！");
                logger.error(LOG + "：更新用户失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * 删除用户
     * @param list
     * @return
     */
    @RequestMapping(value = "/deleteUsers", method = { RequestMethod.POST })
    public @ResponseBody ResultModel deleteUsers(@RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                List<String> listId = new ArrayList<String>();
                for (String userId : list) {
                    String userCode = userService.getUserCodeById(userId);
                    if(!systemConfig.getRootUser().equals(userCode)){
                        if(userService.existDeliverUser(userId)==0){
                            listId.add(userId);
                        }
                    }
                }
                int intResult = userService.deleteUsers(listId);
                if (intResult > 0) {
                    resultModel.setResult(true);
                    resultModel.setDetail("删除用户成功！");
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除用户失败,检测是否用户在发货单已用或是管理员！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除用户失败！");
                logger.error(LOG + "：删除用户失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除用户失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * 将UserPojo转换为UserModel
     * @param userPojo
     * @return
     */
    public UserModel convertUserModel(UserPojo userPojo){
        UserModel userModel = new UserModel();
        if(userPojo != null){
            userModel.setUserId(String.valueOf(userPojo.getUserId()));
            userModel.setUserCode(userPojo.getUserCode());
            userModel.setUserPassword(userPojo.getUserPassword());
            userModel.setUserName(userPojo.getUserName());
            userModel.setUserType(userPojo.getUserType());
            String userTypeName = "";
            switch (userPojo.getUserType()){
                case "1": userTypeName = "发货用户";break;
                case "2": userTypeName = "商务用户";break;
                case "3": userTypeName = "安装用户";break;
                case "4": userTypeName = "维修用户";break;
                case "5": userTypeName = "系统用户";break;
                case "6": userTypeName = "组装用户";break;
                case "7": userTypeName = "测试用户";break;
                case "8": userTypeName = "老化用户";break;
                case "9": userTypeName = "库存用户";break;
                default: userTypeName = "未知用户";break;
            }
            userModel.setUserTypeName(userTypeName);
            userModel.setStopFlag(userPojo.getStopFlag());
            if(userModel.getStopFlag()){
                userModel.setStopFlagName("是");
            }else{
                userModel.setStopFlagName("否");
            }
            userModel.setUserDetail(userPojo.getUserDetail());
            userModel.setEndTime(userPojo.getEndTime());
            userModel.setOptUserId(String.valueOf(userPojo.getOptUserId()));
            userModel.setOptUserName(userPojo.getOptUserName());
            userModel.setOptTime(DateUtil.TimestampToString(userPojo.getOptTime(),DateUtil.DATA_TIME_SECOND));
        }
        return userModel;
    }

    /**
     * 将UserModel转换为UserPojo
     * @param userModel
     * @param encryFlag
     * @param httpSession
     * @return
     */
    public UserPojo convertUserPojo(UserModel userModel, boolean encryFlag,HttpSession httpSession){
        UserPojo userPojo = new UserPojo();
        if(userModel != null) {
            try {
                if (userModel.getUserId() != null && !userModel.getUserId().isEmpty()) {
                    userPojo.setUserId(Integer.valueOf(userModel.getUserId()));
                }
                userPojo.setUserCode(userModel.getUserCode());
                if (userModel.getUserPassword() != null) {
                    if (encryFlag) {
                        userPojo.setUserPassword(ShaUtil.shaEncode(userModel.getUserPassword()));
                    } else {
                        userPojo.setUserPassword(userModel.getUserPassword());
                    }
                }
                userPojo.setUserName(userModel.getUserName());
                userPojo.setUserType(userModel.getUserType());
                userPojo.setStopFlag(userModel.getStopFlag());
                if (userModel.getOptUserId() != null && !userModel.getOptUserId().isEmpty()) {
                    userPojo.setOptUserId(Integer.valueOf(userModel.getOptUserId()));
                }
                UserModel loginUser = (UserModel) httpSession.getAttribute(httpSession.getId());
                if (loginUser != null && loginUser.getUserId() != null && !loginUser.getUserId().isEmpty()) {
                    userPojo.setOptUserId(Integer.valueOf(loginUser.getUserId()));
                }
                userPojo.setEndTime(userModel.getEndTime());
                userPojo.setUserDetail(userModel.getUserDetail());
                if(userModel.getOptTime() != null && !userModel.getOptTime().isEmpty()){
                    userPojo.setOptTime(DateUtil.StringToTimestamp(userModel.getOptTime(), DateUtil.DATA_TIME_SECOND));
                }else{
                    userPojo.setOptTime(DateUtil.GetSystemDateTime(0));
                }
                userPojo.setRowCount(userModel.getRowCount());
                userPojo.setRowIndex(userModel.getRowIndex());
            } catch (Exception e) {
                logger.error(LOG + "：将UserModel转换成UserPojo失败，信息为" + e.getMessage());
            }
        }
        return userPojo;
    }

}
