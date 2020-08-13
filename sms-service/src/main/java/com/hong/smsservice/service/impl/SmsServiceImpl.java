package com.hong.smsservice.service.impl;

import com.hong.smsservice.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liang
 * @description
 * @date 2020/8/5 18:49
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {


    @Override
    public void send() {
        log.info("发送短信成功");
    }

//    private static final String product = "Dysmsapi";
//    private static final String domain = "dysmsapi.aliyuncs.com";
//    private static final String accessKeyId = "null";
//    private static final String accessKeySecret = "null";
//
//    public void sendSMS() {
//        try {
//            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//            IClientProfile profile = DefaultProfile.getProfile("cn-beijing", accessKeyId,
//                    accessKeySecret);
//            DefaultProfile.addEndpoint("cn-beijing", "cn-beijing", product, domain);
//            IAcsClient acsClient = new DefaultAcsClient(profile);
//            //组装请求对象
//            SendSmsRequest request = new SendSmsRequest();
//            request.setPhoneNumbers("15110048927");
//            //必填:短信签名-可在短信控制台中找到
//            request.setSignName("lhy");
//            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
//            request.setTemplateCode(null);
//            request.setTemplateParam("{\"code\":\"123456\", \"ttl\":\"5\"}");
//            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//            log.info("短信响应:{}", JSONObject.toJSONString(sendSmsResponse));
//            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//                log.info("请求成功");
//            }
//        } catch (Exception e) {
//            log.error("请求失败");
//        }
//    }
}
