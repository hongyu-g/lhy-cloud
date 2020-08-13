package com.hong.userservice.controller;

import com.hong.userservice.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liang
 * @description
 * @date 2020/7/16 17:04
 */
@RestController
@Slf4j
@RequestMapping("/web")
public class UserController {

    @Autowired
    private UserService userService;

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/get")
    public Object getUser(@RequestParam Long userId) {
        log.error("线程名称：" + Thread.currentThread().getName());
        if(userId<=0){
            throw new RuntimeException("非法请求");
        }
        return userService.getUser(userId);
    }

    public Object fallback(Long userId) {
        System.out.println("test");
        return "服务器繁忙";
    }
}
