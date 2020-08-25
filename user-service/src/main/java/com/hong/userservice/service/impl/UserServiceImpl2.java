package com.hong.userservice.service.impl;

import com.hong.userservice.dao.UserDAO;
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
public class UserServiceImpl2 {


    @Autowired
    private UserDAO userDAO;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateUser2(Long userId,String name) {
        userDAO.updateUser(userId, name);

    }

}
