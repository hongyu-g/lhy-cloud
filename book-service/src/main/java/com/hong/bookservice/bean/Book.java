package com.hong.bookservice.bean;

import lombok.Data;

/**
 * @author liang
 * @description
 * @date 2020/6/17 18:15
 */
@Data
public class Book {

    private String sn;

    private String name;

    private String desc;

    private Double price;
}
