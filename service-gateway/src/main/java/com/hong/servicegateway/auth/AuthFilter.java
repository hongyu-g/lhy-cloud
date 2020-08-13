package com.hong.servicegateway.auth;

import com.google.common.collect.Lists;
import com.hong.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author liang
 * @description
 * @date 2020/7/16 15:03
 */
@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();
        //忽略以下url请求
        List<String> pages = Lists.newArrayList("/user/open/login");
        for (String page : pages) {
            if (page.equals(uri)) {
                //直接通过，传输到下一级
                return chain.filter(exchange);
            }
        }
        //从请求头中取得token
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(authorization)) {
            return error(exchange.getResponse(), "未授权");
        }

        //请求中的token是否在redis中存在
        Long userId = redisUtil.get(authorization, Long.class);
        if (Objects.isNull(userId)) {
            return error(exchange.getResponse(), "授权过期");
        }
        //将登录信息保存到下一级
        ServerHttpRequest newRequest = exchange.getRequest().mutate().header("userId", String.valueOf(userId)).build();
        ServerWebExchange newExchange =
                exchange.mutate().request(newRequest).build();
        return chain.filter(newExchange);
    }


    /**
     * 返回错误
     * https://www.cnblogs.com/wunaozai/p/12522485.html
     * @param response
     * @param json
     * @return
     */
    private Mono<Void> error(ServerHttpResponse response, String json) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 这是Ordered接口的中的方法
     * 过滤器有一个优先级的问题，这个值越小，优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return -100;
    }
}
