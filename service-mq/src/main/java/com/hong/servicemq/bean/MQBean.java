package com.hong.servicemq.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liang
 * @description
 * @date 2020/7/21 14:21
 */
@Data
@Accessors(chain = true)
public class MQBean {

    /**
     * 消息内容
     */
    private String data;

    /**
     * 主题
     */
    private String topic;

    /**
     * 二级标签
     */
    private String tags;

    /**
     * 唯一主键
     */
    private String keys;

    private Integer msgType;

    /**
     * 延迟投递时间，单位秒
     */
    private Integer delayTime;

    /**
     * 业务id
     */
    private Long businessId;

    /**
     * 消息发送类型 1-同步、2-异步、3-单向
     */
    private Integer sendType;
}
