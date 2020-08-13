package com.hong.rankservice.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liang
 * @description
 * @date 2020/7/23 17:26
 */
@Data
@Accessors(chain = true)
public class UserCheckStatistic {

    private Long id;

    private Long userId;

    private Integer challengeSuccessLordNum;

    private Integer challengeMysteryNum;

    private Integer quantity;
}
