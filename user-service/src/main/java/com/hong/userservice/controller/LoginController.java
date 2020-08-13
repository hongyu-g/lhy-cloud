package com.hong.userservice.controller;

import com.hong.common.data.Response;
import com.hong.userservice.bean.UserPO;
import com.hong.userservice.service.UserService;
import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liang
 * @description
 * @date 2020/7/3 16:29
 */
@RestController
@Slf4j
@RequestMapping("/open")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody UserPO po) {
        return new Response<Boolean>().success(userService.login(po));
    }


}
