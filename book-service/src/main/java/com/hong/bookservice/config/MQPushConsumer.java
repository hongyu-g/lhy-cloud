package com.hong.bookservice.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author liang
 * @description
 * @date 2020/6/23 16:43
 */
@Component
@Slf4j
public class MQPushConsumer implements MessageListenerOrderly {

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;

    private final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TestRocketMQPushConsumer");

    private AtomicLong num = new AtomicLong(0);

    /**
     * 初始化
     */
    @PostConstruct
    public void start() {
        try {
            log.info("MQ 启动消费者");
            consumer.setNamesrvAddr(namesrvAddr);
            // 从消息队列尾开始消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            //订阅主题
            consumer.subscribe("TopicTest", "*");
            //注册消息监听器
            consumer.registerMessageListener(this);
            consumer.start();
        } catch (MQClientException e) {
            log.error("MQ  启动消费者失败", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        /**
         *  设置是否自动提交： 默认自动提交，提交之后消息就不能够被再次消费。
         *  非自动提交时,消息可能会被重复消费
         */
        context.setAutoCommit(false);
        this.num.incrementAndGet();
        try {
            for (MessageExt msg : msgs) {
                String messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                log.info("MQ 消费者接收新信息:{}", JSONObject.toJSONString(new Object[]{
                        msg.getMsgId(), msg.getTopic()
                        , msg.getTags(), msg.getKeys(), messageBody
                }));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (this.num.get() % 3 == 0) {
            // return ConsumeOrderlyStatus.ROLLBACK;
        } else if (this.num.get() % 4 == 0) {
            return ConsumeOrderlyStatus.COMMIT;
        } else if (this.num.get() % 5 == 0) {
            context.setSuspendCurrentQueueTimeMillis(3000);
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        // 非主动提交的时候，SUCCESS不会导致队列消息提交，消息未提交就可以被循环消费
        return ConsumeOrderlyStatus.SUCCESS;
    }

    @PreDestroy
    public void stop() {
        if (consumer != null) {
            consumer.shutdown();
            log.info("MQ 关闭消费者");
        }
    }
}
