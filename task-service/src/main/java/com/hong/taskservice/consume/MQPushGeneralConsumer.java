package com.hong.taskservice.consume;

import com.hong.taskservice.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/6/23 16:43
 */
@Slf4j
@Component
public class MQPushGeneralConsumer implements MessageListenerConcurrently {


    @Autowired
    private TaskService taskService;

    /**
     * 消费消息
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        int index = 0;
        log.info("消费顺序消息:{}", msgs);
        try {
            for (; index < msgs.size(); index++) {
                MessageExt msg = msgs.get(index);
                String messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                taskService.handleMessage(messageBody);
            }
        } catch (Exception e) {
            log.error("MQ 消费消息异常", e);
            //return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        } finally {
            if (index < msgs.size()) {
                context.setAckIndex(index + 1);
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }


    /**
     * 消息消费失败，重试逻辑
     * rocketMQ采用ACK机制保证消息消费成功
     */
    private void failHandle() {

    }
}
