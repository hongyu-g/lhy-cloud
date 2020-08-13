package com.hong.smsservice.service;

/**
 * @author liang
 * @description
 * @date 2020/8/5 18:48
 */
public interface SmsService {

    /**
     * 发送短信
     * 手机号验证，短信超限检查(一分钟不能重新发送)，去重，平台区分
     * 短信白名单，测试使用，拦截，限流，发送异常处理
     */
    void send();
}
