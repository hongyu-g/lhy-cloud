package com.hong.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author liang
 * @description
 * @date 2020/8/31 15:58
 */
@Component
public class RedisLimiterUtils {

    /**
     * 以固定的速率生成key，获取到令牌的请求放行
     * 令牌桶存满？ 丢弃多余的令牌
     * 令牌桶空了？ 丢弃获取令牌的请求
     * <p>
     * <p>
     * <p>
     * 根据redis保存的上次的请求时间和当前时间比较，如果相差大于的产生令牌的时间则再次产生令牌，此时的桶容量为当前令牌+产生的令牌
     */

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    private String key = "token_bucket";


    /**
     * lua脚本，限流
     */
    private final static String TEXT = "" +
            "local ratelimit_info = redis.pcall('HMGET',KEYS[1],'last_time','current_token')\n" +
            "local last_time = ratelimit_info[1]\n" +
            "local current_token = tonumber(ratelimit_info[2])\n" +
            "local max_token = tonumber(ARGV[1])\n" +
            "local token_rate = tonumber(ARGV[2])\n" +
            "local current_time = tonumber(ARGV[3])\n" +
            "if current_token == nil then\n" +
            "  current_token = max_token\n" +
            "  last_time = current_time\n" +
            "else\n" +
            "  local past_time = current_time-last_time\n" +
            "  \n" +
            "  if past_time>1000 then\n" +
            "\t  current_token = current_token+token_rate\n" +
            "\t  last_time = current_time\n" +
            "  end\n" +
            "\n" +
            "  if current_token>max_token then\n" +
            "    current_token = max_token\n" +
            "\tlast_time = current_time\n" +
            "  end\n" +
            "end\n" +
            "\n" +
            "local result = 0\n" +
            "if(current_token>0) then\n" +
            "  result = 1\n" +
            "  current_token = current_token-1\n" +
            "  last_time = current_time\n" +
            "end\n" +
            "redis.call('HMSET',KEYS[1],'last_time',last_time,'current_token',current_token)\n" +
            "return result";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取令牌
     *
     * @param key  请求id
     * @param max  最大能同时承受多少的并发（桶容量）
     * @param rate 每秒生成多少的令牌
     * @return 获取令牌返回true，没有获取返回false
     */
    public boolean tryAcquire(String key, int max, int rate) {
        List<String> keyList = new ArrayList<>(1);
        keyList.add(key);
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptText(TEXT);
        return Long.valueOf(1).equals(redisTemplate.execute(script, keyList, Integer.toString(max), Integer.toString(rate),
                Long.toString(System.currentTimeMillis())));
    }


    /**
     * @param key
     * @param max  最大能同时承受多少的并发（桶容量）
     * @param rate 每秒生成多少的令牌
     * @return
     */
    public boolean tryAcquire2(String key, int max, int rate) {
        int count;
        long time;
        String timeStr = (String) hashOperations.get(key, "last_time");
        String countStr = (String) hashOperations.get(key, "current_token");
        if (Objects.isNull(timeStr)) {
            time = System.currentTimeMillis();
        } else {
            time = Long.valueOf(timeStr);
        }
        if (Objects.isNull(countStr)) {
            count = max;
        } else {
            count = Integer.valueOf(countStr);
        }
        if (System.currentTimeMillis() - time > 1000) {
            System.out.println("生产令牌");
            count = count + rate;
            time = System.currentTimeMillis();
        }
        if (count > max) {
            count = max;
            System.out.println("令牌桶存满");
        }
        if (count <= 0) {
            return false;
        }
        hashOperations.put(key, "current_token", Integer.toString(count - 1));
        hashOperations.put(key, "last_time", Long.toString(time));
        return true;
    }
}
