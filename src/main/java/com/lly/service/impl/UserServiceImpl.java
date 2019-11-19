package com.lly.service.impl;

import com.lly.dao.IUserDao;
import com.lly.pojo.UserPojo;
import com.lly.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Wanglei on 2018/1/24.
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public int getUserCount(UserPojo userPojo) {
        return userDao.getUserCount(userPojo);
    }

    @Override
    public List<UserPojo> getUser(UserPojo userPojo) {
        return userDao.getUser(userPojo);
    }

    @Override
    public int insertUser(UserPojo userPojo) throws Exception {
        int result = 0;
        if(userPojo != null){
            result = userDao.insertUser(userPojo);
        }else{
            result = 0;
        }
        return result;
    }

    @Override
    public int updateUser(UserPojo userPojo) throws Exception {
        return userDao.updateUser(userPojo);
    }

    @Override
    public int updateUserPassword(String userId, String userPassword) throws Exception {
        return userDao.updateUserPassword(userId,userPassword);
    }

    @Override
    public int updateUserLoginTime(String userId, String endTime) {
        return userDao.updateUserLoginTime(userId,endTime);
    }

    @Override
    public int deleteUsers(List<String> idList) throws Exception {
        return userDao.deleteUser(idList);
    }

    @Override
    public int existUpdateUser(String userId, String userCode) {
        return userDao.existUpdateUser(userId, userCode);
    }

    @Override
    public String getUserCodeById(String userId) {
        return userDao.getUserCodeById(userId);
    }

    @Override
    public String getUserTypeById(String userId) {
        return userDao.getUserTypeById(userId);
    }

    @Override
    public int existDeliverUser(String userId) {
        return userDao.existDeliverUser(userId);
    }
}
