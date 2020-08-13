package com.hong.common.data;

import java.io.Serializable;

/**
 * @author liang
 * @description
 * @date 2020/7/16 15:09
 */
public class Response<T> implements Serializable {

    private int code;
    private T data;
    private String message;


    public Response(){

    }

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public Response success(T data) {
        this.message = "ok";
        this.code = 200;
        this.data = data;
        return this;
    }

    public Response error(String message) {
        this.message = message;
        this.code = 500;
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
