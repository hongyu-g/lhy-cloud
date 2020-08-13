package com.hong.bookservice.bean;

import lombok.Data;

/**
 * @author liang
 * @description
 * @date 2020/6/12 18:35
 */
@Data
public class EsEntity<T> {

    private String id;

    private T data;
}
