package com.hong.smsservice.controller;

import com.hong.smsservice.bean.SMSDto;
import com.hong.smsservice.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liang
 * @description
 * @date 2020/8/5 17:27
 */
@RestController
@RequestMapping
@Slf4j
public class SmsController {


    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public Object send(@RequestBody SMSDto smsDto, HttpServletRequest request) {
        request.setAttribute("access_limit_customize", smsDto.getTel());
        smsService.send();
        return true;
    }


}
