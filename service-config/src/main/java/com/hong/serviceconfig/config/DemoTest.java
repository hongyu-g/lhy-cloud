package com.hong.serviceconfig.config;

import com.hong.serviceconfig.bean.Demo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DemoTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigTest.class);
        Demo demo = applicationContext.getBean("getDemo",Demo.class);
        System.out.println(demo.getId());
    }
}
