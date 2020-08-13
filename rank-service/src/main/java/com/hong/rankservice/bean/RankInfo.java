package com.hong.rankservice.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liang
 * @description
 * @date 2020/7/22 15:07
 */
@Data
@Accessors(chain = true)
public class RankInfo {

    private Long userId;

    private Long checkPointId;

    private Integer challengeSuccessLordNum;

    private Integer challengeMysteryNum;

    private Integer ranking;

    private Integer quantity;

    /**
     * 1-普通，2-魔王，3-神秘
     */
    private Integer type;
}
