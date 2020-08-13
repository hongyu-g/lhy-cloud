package com.hong.taskservice.consume;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author liang
 * @description
 * @date 2020/7/21 18:43
 */
@Component
@Slf4j
public class ConsumerConfig {

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    private final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();


    @Autowired
    private MQPushGeneralConsumer mqPushConsumer;


    /**
     * 初始化
     */
    @PostConstruct
    public void start() {
        try {
            log.info("MQ 启动消费者");
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumerGroup(groupName);
            //初始化设置时生效
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            //消费模式
            consumer.setMessageModel(MessageModel.CLUSTERING);
            //订阅主题
            consumer.subscribe(topic, "*");
            //注册消息监听器
            consumer.registerMessageListener(mqPushConsumer);
            //启动消费端
            consumer.start();
        } catch (MQClientException e) {
            log.error("MQ  启动消费者失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    @PreDestroy
    public void stop() {
        if (consumer != null) {
            consumer.shutdown();
            log.info("MQ 关闭消费者");
        }
    }
}
