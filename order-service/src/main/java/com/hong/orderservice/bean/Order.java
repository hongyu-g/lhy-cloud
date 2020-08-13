package com.hong.orderservice.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liang
 * @description
 * @date 2020/7/21 11:29
 */
@Data
@Accessors(chain = true)
public class Order {

    /**
     * VIP抢购剩余数量
     */
    private Integer count;

    private Long userId;


    private Long checkPointId;
}
