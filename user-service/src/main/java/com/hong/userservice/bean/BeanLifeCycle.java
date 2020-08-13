package com.hong.userservice.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liang
 * @description
 * @date 2020/7/24 18:43
 */
public class BeanLifeCycle {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beans.xml");
        System.out.println("容器初始化成功");


        Demo1 demo1 = applicationContext.getBean("demo1", Demo1.class);
        System.out.println(demo1);

//        System.out.println("开始关闭容器");
//        ((ClassPathXmlApplicationContext) applicationContext).registerShutdownHook();
    }
}
