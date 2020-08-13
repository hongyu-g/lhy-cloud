package com.hong.userservice.service;


import com.hong.userservice.bean.User;
import com.hong.userservice.bean.UserPO;

public interface UserService {

    Boolean login(UserPO po);

    User getUser(Long userId);

    void updateUser(Long userId, String name);
}
