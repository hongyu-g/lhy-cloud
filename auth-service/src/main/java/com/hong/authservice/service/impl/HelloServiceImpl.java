package com.hong.authservice.service.impl;

import com.hong.authservice.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * @author liang
 * @description
 * @date 2020/7/6 13:33
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello() {
        System.out.println("hello world");
        sayHello();
        if(1==1){
           throw new RuntimeException("test");
        }
        return "hello";
    }

    @Override
    public void sayHello() {
        System.out.println("say hello");
    }
}
