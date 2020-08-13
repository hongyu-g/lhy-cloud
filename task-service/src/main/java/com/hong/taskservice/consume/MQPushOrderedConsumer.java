package com.hong.taskservice.consume;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/7/21 18:47
 */
@Slf4j
@Component
public class MQPushOrderedConsumer implements MessageListenerOrderly {

    /**
     * 有序消费：将同一个topic下的消息发送到同一个队列中（全局有序消息）
     * 将同一个业务下的消息发送到同一个队列中（局部有序消息，提高吞吐量），如通过业务id根据队列数量取余
     */
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        log.info("消费顺序消息:{}", msgs);
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
