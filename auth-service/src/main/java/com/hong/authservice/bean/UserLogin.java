package com.hong.authservice.bean;

/**
 * @author liang
 * @description
 * @date 2020/7/8 16:06
 */
public class UserLogin {

    private Long userId;

    private String userName;

    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
