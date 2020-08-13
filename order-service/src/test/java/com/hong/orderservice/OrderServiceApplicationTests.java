package com.hong.orderservice;

import com.hong.common.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    private static final String key = "vip_count";

    @Test
    void contextLoads() {
        long offset = 123;
        redisUtil.getRedisTemplate().opsForValue().setBit(key, offset, true);
        System.out.println(redisUtil.getRedisTemplate().opsForValue().getBit(key, offset));
    }

}
