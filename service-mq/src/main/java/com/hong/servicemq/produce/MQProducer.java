package com.hong.servicemq.produce;

import com.alibaba.fastjson.JSONObject;
import com.hong.servicemq.bean.MQBean;
import com.hong.servicemq.bean.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/6/23 16:42
 */
@Component
@Slf4j
public class MQProducer {

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    private final DefaultMQProducer producer = new DefaultMQProducer();


    /**
     * 初始化
     */
    @PostConstruct
    public void start() {
        try {
            log.info("MQ: 启动生产者");
            producer.setNamesrvAddr(namesrvAddr);
            producer.setProducerGroup(groupName);
            producer.start();
        } catch (MQClientException e) {
            log.error("MQ: 启动生产者失败", e.getResponseCode(), e.getErrorMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 消息类型：普通消息（无序消息）、有序消息、延时消息
     *
     * 消息发送：同步发送、异步发送、单向发送
     */


    /**
     * 生产消息
     *
     * @param mqBean
     */
    public void sendMessage(MQBean mqBean) {
        try {
            byte[] messageBody = mqBean.getData().getBytes(RemotingHelper.DEFAULT_CHARSET);
            Message mqMsg = new Message(mqBean.getTopic(), mqBean.getTags(), mqBean.getKeys(), messageBody);
            if (MessageType.GENERAL_MESSAGE.getId() == mqBean.getMsgType()) {
                sendSyncMessage(mqMsg);
            }
            if (MessageType.ORDERED_MESSAGE.getId() == mqBean.getMsgType()) {
                sendOrderedMessage(mqMsg, mqBean.getBusinessId());
            }
            if (MessageType.DELAY_MESSAGE.getId() == mqBean.getMsgType()) {
                // 设置投递的延迟等级
                mqMsg.setDelayTimeLevel(mqBean.getDelayTimeLevel());
                sendDelayMessage(mqMsg);
            }
        } catch (Exception e) {
            log.error("MQ 发送消息异常", e);
        }
    }

    /**
     * 生产普通消息、无序消息
     */
    private void sendSyncMessage(Message mqMsg) throws Exception {
        SendResult sendResult = producer.send(mqMsg);
        log.info("MQ: 生产者同步发送普通消息:{}", sendResult);
    }


    private void sendAsyncMessage(Message mqMsg) throws Exception {
        producer.send(mqMsg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("MQ: 生产者异步发送消息成功:{}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.info("MQ: 生产者异步发送消息异常:{}", e);
            }
        });
    }

    private void sendOneWayMessage(Message mqMsg) throws Exception {
        producer.setRetryTimesWhenSendAsyncFailed(0);
        producer.sendOneway(mqMsg);
        log.info("MQ: 生产者单向发送消息:{}");
    }


    /**
     * 生产顺序消息
     */
    private void sendOrderedMessage(Message mqMsg, Long businessId) throws Exception {
        producer.send(mqMsg, new MessageQueueSelector() {
            /**
             * @param mqs topic下的所有队列
             * @param msg
             * @param arg
             */
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                long businessId = (long) arg;
                int index = (int) businessId % mqs.size();
                //这里根据前面的id对队列集合大小求余来返回所对应的队列
                return mqs.get(index);
            }
        }, businessId, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("MQ: 生产者异步发送顺序消息:{}", JSONObject.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable e) {
                log.info("MQ: 生产者异步发送顺序消息异常:{}", e);
            }
        });

    }


    private void sendDelayMessage(Message mqMsg) throws Exception {
        SendResult sendResult = producer.send(mqMsg);
        log.info("MQ: 生产者发送延时消息:{}", JSONObject.toJSONString(sendResult));

    }
}