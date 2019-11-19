package com.lly.service;

import com.lly.pojo.UserPojo;

import java.util.List;

public interface IUserService {

    /**
     * 查询用户个数
     * @param userPojo
     * @return
     */
    int getUserCount(UserPojo userPojo);

    /**
     * 查询用户数据
     * @param userPojo
     * @return
     */
    List<UserPojo> getUser(UserPojo userPojo);

    /**
     * 添加用户
     * @param userPojo
     * @return
     */
    int insertUser(UserPojo userPojo) throws Exception;

    /**
     * 更新用户
     * @param userPojo
     * @return
     */
    int updateUser(UserPojo userPojo) throws Exception;

    /**
     *
     * @param userId
     * @param userPassword
     * @return
     * @throws Exception
     */
    int updateUserPassword(String userId,String userPassword) throws Exception;

    /**
     * 更新用户登录时间
     * @param userId
     * @param endTime
     * @return
     */
    int updateUserLoginTime(String userId,String endTime);

    /**
     * 删除用户
     * @param idList
     * @return
     */
    int deleteUsers(List<String> idList) throws Exception;

    /**
     * 存在此用户（更新时）
     * @param userId
     * @param userCode
     * @return
     */
    int existUpdateUser(String userId,String userCode);

    /**
     * 通过userId获取userCode
     * @param userId
     * @return
     */
    String getUserCodeById(String userId);

    /**
     * 通过userId获取userType
     * @param userId
     * @return
     */
    String getUserTypeById(String userId);

    /**
     * 检查是否存在订单已用用户
     * @param userId
     * @return
     */
    int existDeliverUser(String userId);

}
