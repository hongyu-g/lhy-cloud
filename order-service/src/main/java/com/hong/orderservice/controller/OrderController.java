package com.hong.orderservice.controller;

import com.hong.common.feign.UserFeign;
import com.hong.orderservice.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liang
 * @description
 * @date 2020/7/21 11:24
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private UserFeign userFeign;


    @Autowired
    public RestTemplate restTemplate;

    /**
     * Feign+Hystrix+Ribbon
     * @param userId
     * @return
     */
    @PostMapping("/createOrder")
    public Object createOrder(@RequestParam Long userId) {
        return userFeign.getUser(userId);
    }


    /**
     * HystrixCommand+Hystrix+Ribbon+RestTemplate
     */
    @HystrixCommand(fallbackMethod = "fallback",
    commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"))
    @PostMapping("/test")
    public Object createOrderRest(@RequestParam Long userId) {
        return restTemplate.getForEntity("http://user-service/user/web/get?userId=" + userId, String.class).getBody();
    }


    public Object fallback(Long userId) {
        return "服务熔断，返回默认值";
    }

}
