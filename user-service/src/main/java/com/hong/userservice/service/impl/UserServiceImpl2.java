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


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void updateUser2(String name) {
        userDAO.updateUser(227L, name);
        if (1 == 1) {
            throw new RuntimeException("test");
        }
    }

}
