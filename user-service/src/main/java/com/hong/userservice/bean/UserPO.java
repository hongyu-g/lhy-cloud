package com.hong.userservice.bean;

import java.io.Serializable;

/**
 * @author liang
 * @description
 * @date 2020/7/8 15:06
 */

public class UserPO implements Serializable {

    private Long userId;

    private String name;

    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
