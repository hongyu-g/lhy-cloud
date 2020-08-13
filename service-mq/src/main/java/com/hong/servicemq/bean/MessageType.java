package com.hong.servicemq.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liang
 * @description
 * @date 2020/7/21 14:29
 */
@AllArgsConstructor
@Getter
public enum MessageType {

    /**
     * MQ消息类型
     */
    GENERAL_MESSAGE(1, "普通/无序消息"),
    ORDERED_MESSAGE(2, "有序消息"),
    DELAY_MESSAGE(3, "延时消息"),;

    private int id;
    private String value;
}
