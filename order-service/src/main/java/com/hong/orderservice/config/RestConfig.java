package com.hong.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author liang
 * @description
 * @date 2020/9/3 15:59
 */
@Configuration
public class RestConfig {

    /**
     * @LoadBalanced 开启负载均衡
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 负载均衡策略
     * RoundRobinRule 轮询（默认）
     */
//    @Bean
//    public IRule getIRule() {
//        return new MyRule();
//    }

}
