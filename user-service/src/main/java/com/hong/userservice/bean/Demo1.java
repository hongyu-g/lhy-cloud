package com.hong.userservice.bean;

public class Demo1 {

    private Demo2 demo2;

    public void setDemo2(Demo2 demo2) {
        System.out.println("注入Demo1属性 -》 demo2");
        this.demo2 = demo2;
    }
}
