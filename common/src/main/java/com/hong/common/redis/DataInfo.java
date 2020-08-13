package com.hong.common.redis;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liang
 * @description
 * @date 2020/7/22 17:35
 */
@Accessors(chain = true)
@Data
public class DataInfo {

    private Object value;

    private Double score;
}
