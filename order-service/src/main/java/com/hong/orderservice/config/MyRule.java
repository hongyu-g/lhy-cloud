package com.hong.orderservice.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;

/**
 * @author liang
 * @description
 * @date 2020/9/3 16:00
 */
public class MyRule {

    @Bean
    public IRule getLoadBalancedRule() {
        return new RoundRobinRule();
    }
}
