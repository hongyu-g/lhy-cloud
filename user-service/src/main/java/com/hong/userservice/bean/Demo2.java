package com.hong.userservice.bean;

public class Demo2 {


    private Demo1 demo1;

    public void setDemo3(Demo1 demo1) {
        System.out.println("注入Demo2属性  -》 demo1");
        this.demo1 = demo1;
    }
}
