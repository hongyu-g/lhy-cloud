package com.hong.common.exception;

/**
 * @author liang
 * @description
 * @date 2020/8/6 16:08
 */
public class AccessLimitException extends RuntimeException{
    public AccessLimitException(String message){
        super(message);
    }
}
