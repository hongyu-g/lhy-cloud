package com.hong.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hong.common.bean.MQBean;
import com.hong.common.bean.MessageType;
import com.hong.common.feign.MQFeignClient;
import com.hong.orderservice.bean.Order;
import com.hong.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author liang
 * @description
 * @date 2020/7/21 11:28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MQFeignClient mqFeignClient;


    @Value("${rocketmq.topic}")
    private String topic;
    @Value("${rocketmq.topic.sms}")
    private String smsTopic;


    @Override
    public void create(Long userId) {
        Order order = new Order();
        order.setUserId(userId);
        //预订单
        MQBean mqBean = new MQBean().setData(JSONObject.toJSONString(order)).setTopic(smsTopic)
                .setMsgType(MessageType.DELAY_MESSAGE.getId()).setBusinessId(userId).setDelayTimeLevel(5);
        mqFeignClient.produce(mqBean);
    }
}
