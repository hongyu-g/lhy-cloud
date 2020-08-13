package com.hong.servicemq;

import com.hong.servicemq.bean.MQBean;
import com.hong.servicemq.bean.MessageType;
import com.hong.servicemq.produce.MQProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceMqApplicationTests {

    @Autowired
    private MQProducer mqProducer;


    @Test
    void contextLoads() {
        MQBean mqBean = new MQBean().setMsgType(MessageType.GENERAL_MESSAGE.getId());
        mqBean.setTopic("vip").setData(1 + "");
        mqProducer.sendMessage(mqBean);
    }

}
