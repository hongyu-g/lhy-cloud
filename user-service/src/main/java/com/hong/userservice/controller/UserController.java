package com.hong.userservice.controller;

import com.hong.userservice.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.port}")
    private String port;

    @GetMapping("/get")
    public Object getUser(@RequestParam Long userId) {
        if (userId <= 0) {
            throw new RuntimeException("非法请求");
        }
        try {
            Thread.sleep(5000);
        }catch (Exception e){

        }
        return "访问user-service，port:" + port;
    }

    public Object fallback(Long userId) {
        System.out.println("test");
        return "服务器繁忙";
    }


    /**
     * 自定义熔断器超时时间
     *
     * @param userId
     * @return
     */
    @HystrixCommand(
            /**
             * 降级方法，入参和返回值需要和原方法一致，否则会报错
            */
            fallbackMethod = "fallback",
            commandProperties = {
                    /**
                     * 隔离策略：线程隔离（默认）、信号量隔离
                     */
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    /**
                     * 表示请求线程总超时时间，如果超过这个设置的时间hystrix就会调用fallback方法。value的参数为毫秒，默认值为1000ms
                     */
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
                    /**
                     * 熔断器，该属性用来设置在滚动时间窗中，断路器的最小请求数，默认值：20
                     */
                    //@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1")
    })
    @GetMapping("/query")
    public Object queryUser(@RequestParam Long userId) {
        System.out.println(Thread.currentThread().getName());
        if (userId <= 0) {
            throw new RuntimeException("非法请求");
        }
        try {
            Thread.sleep(2000);
        }catch (Exception e){

        }
        return userService.getUser(userId);
    }

}
