package com.tcb.dao;

import com.tcb.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WangLei
 * @Description:
 * @Date: Create in 2018/1/24 10:35
 * @Modify by WangLei
 */
@Repository
public interface IUserDao {

    /**
     * 查询用户个数
     * @param userPojo
     * @return
     */
    int getUserCount(@Param("userPojo")UserPojo userPojo);

    /**
     * 查询用户数据
     * @param userPojo
     * @return
     */
    List<UserPojo> getUser(@Param("userPojo")UserPojo userPojo);

    /**
     * 新增用户数据
     * @param userPojo
     * @return
     */
    int insertUser(@Param("userPojo")UserPojo userPojo);

    /**
     * 更新用户数据
     * @param userPojo
     * @return
     */
    int updateUser(@Param("userPojo")UserPojo userPojo);

    /**
     * 更新用户密码
     * @param userId
     * @param userPassword
     * @return
     */
    int updateUserPassword(@Param("userId")String userId,@Param("userPassword")String userPassword);

    /**
     * 更新用户登录时间
     * @param userId
     * @param endTime
     * @return
     */
    int updateUserLoginTime(@Param("userId")String userId,@Param("endTime")String endTime);

    /**
     * 删除用户数据
     * @param idList
     * @return
     */
    int deleteUser(@Param("idList")List<String> idList);

    /**
     * 存在此用户（更新时）
     * @param userId
     * @param userCode
     * @return
     */
    int existUpdateUser(@Param("userId")String userId, @Param("userCode")String userCode);

    /**
     * 通过userId获取userCode
     * @param userId
     * @return
     */
    String getUserCodeById(@Param("userId")String userId);

    /**
     * 通过userId获取userType
     * @param userId
     * @return
     */
    String getUserTypeById(@Param("userId")String userId);

    /**
     * 检查是否存在订单已用用户
     * @param userId
     * @return
     */
    int existDeliverUser(@Param("userId")String userId);

}
