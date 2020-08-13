package com.hong.servicemq.controller;

import com.hong.servicemq.bean.MQBean;
import com.hong.servicemq.produce.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liang
 * @description
 * @date 2020/7/21 14:47
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private MQProducer mqProducer;

    /**
     * 生产消息
     * @param mqBean
     * @return
     */
    @PostMapping("/produce")
    public boolean produce(@RequestBody MQBean mqBean) {
        if (StringUtils.isBlank(mqBean.getTopic()) || StringUtils.isBlank(mqBean.getData()) || mqBean.getMsgType() == null) {
            return false;
        }
        mqProducer.sendMessage(mqBean);
        return true;
    }


}
