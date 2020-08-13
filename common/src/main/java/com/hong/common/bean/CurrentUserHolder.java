package com.hong.common.bean;

/**
 * @author liang
 * @description
 * @date 2020/6/18 17:47
 */
public class CurrentUserHolder {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

    public static Long getHolder() {
        return USER_ID.get();
    }

    public static void setHolder(Long userId) {
        USER_ID.set(userId);
    }

    public static void remove() {
        USER_ID.remove();
    }
}
