package com.hong.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liang
 * @description
 * @date 2020/6/9 16:43
 */

@RestController
@RefreshScope
@Slf4j
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public Object test() {
        System.out.println("线程名称：" + Thread.currentThread().getName());
        if(1==1){
            throw new RuntimeException("test");
        }
        return "user hello world，端口：" + port;
    }


    /**
     * 配置熔断后，正常访问，服务隔离机制
     *
     * @return
     */
    @GetMapping("/otherService")
    public Object otherService() {
        System.out.println("线程名称：" + Thread.currentThread().getName());
        return "我是其他服务";
    }


}
