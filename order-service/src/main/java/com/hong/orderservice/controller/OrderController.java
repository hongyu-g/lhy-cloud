package com.hong.orderservice.controller;

import com.hong.common.feign.UserFeign;
import com.hong.orderservice.service.OrderService;
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

    @PostMapping("/createOrder")
    public Object createOrder(@RequestParam Long userId) {
        //return restTemplate.getForEntity("http://user-service/user/web/get?userId=" + userId, String.class).getBody();
        return userFeign.getUser(userId);
    }


}
