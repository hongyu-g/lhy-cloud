package com.hong.rankservice.consume;

import com.alibaba.fastjson.JSONObject;
import com.hong.rankservice.bean.RankInfo;
import com.hong.rankservice.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/6/23 16:43
 */
@Component
@Slf4j
public class MQPushConsumer implements MessageListenerConcurrently {

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    private final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();


    @Autowired
    private RankService rankService;


    /**
     * 初始化
     */
    @PostConstruct
    public void start() {
        try {
            log.info("MQ 启动消费者");
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.setConsumerGroup(groupName);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            //集群消费模式
            consumer.setMessageModel(MessageModel.CLUSTERING);
            //订阅主题
            consumer.subscribe(topic, "*");
            //注册消息监听器
            consumer.registerMessageListener(this);
            //启动消费端
            consumer.start();
        } catch (MQClientException e) {
            log.error("MQ  启动消费者失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 消费消息
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        int times = 0;
        try {
            for (int i = 0; i < msgs.size(); i++) {
                MessageExt messageExt = msgs.get(i);
                log.info("MQ 消费者接收新信息:{}", messageExt);
                String key = messageExt.getKeys();
                //消息重试消费次数
                times = messageExt.getReconsumeTimes();
                long offset = messageExt.getQueueOffset();
                String maxOffset = messageExt.getProperty(MessageConst.PROPERTY_MAX_OFFSET);
                long diff = Long.parseLong(maxOffset) - offset;
                if (diff > 100000) {
                    log.info("MQ 消息堆积情况的特殊处理");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String data = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                RankInfo rankInfo = JSONObject.parseObject(data, RankInfo.class);
                if (rankInfo.getUserId() == 5274L) {
                    throw new RuntimeException("test");
                }
                rankService.add(rankInfo.setQuantity(1));
            }
        } catch (Exception e) {
            log.error("MQ 重试次数:{},消费异常:{}", times, e);
            //记录表
            if (times >= 2) {
                System.out.println("重试次数大于2，记录数据库，通知开发人员");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    @PreDestroy
    public void stop() {
        if (consumer != null) {
            consumer.shutdown();
            log.info("MQ 关闭消费者");
        }
    }

}
