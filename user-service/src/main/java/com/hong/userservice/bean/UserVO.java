package com.hong.userservice.bean;

import java.io.Serializable;

/**
 * @author liang
 * @description
 * @date 2020/7/8 15:10
 */
public class UserVO implements Serializable {

    private Long userId;

    private String token;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
