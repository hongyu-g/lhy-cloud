package com.hong.userservice.service.impl;

import com.hong.common.redis.RedisUtil;
import com.hong.userservice.bean.User;
import com.hong.userservice.bean.UserPO;
import com.hong.userservice.dao.UserDAO;
import com.hong.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liang
 * @description
 * @date 2020/6/18 17:51
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO userDAO;


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Boolean login(UserPO po) {
        redisUtil.set("user_login_" + po.getUserId(), po.getUserId(), 60);
        return true;
    }

    @Override
    public User getUser(Long userId) {
        return userDAO.getUser(userId);
    }


    @Autowired
    private  UserServiceImpl2 userServiceImpl2;

    @Override
    public void updateUser(Long userId, String name) {
        userDAO.updateUser(userId, name);
        userServiceImpl2.updateUser2(name);

    }



}
