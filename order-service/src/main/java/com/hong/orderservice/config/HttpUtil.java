package com.hong.orderservice.config;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author liang
 * @description
 * @date 2020/9/7 16:45
 */
@Component
public class HttpUtil {


    @Autowired
    public RestTemplate restTemplate;


    /**
     * HystrixCommand+Hystrix+Ribbon+RestTemplate
     * 新建隔离的线程池
     */
    @HystrixCommand(
            /**
             * 降级方法，入参和返回值需要和原方法一致，否则会报错
            */
            fallbackMethod = "fallback",
            commandProperties = {
                    /**
                     * 隔离策略：线程池隔离（默认）、信号量隔离（限制并发访问）
                     */
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    //当隔离策略使用SEMAPHORE时，最大的并发请求量，如果请求超过这个最大值将拒绝后续的请求，默认值为10.
                    //@HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "5"),
                    /**
                     * 表示请求线程总超时时间，如果超过这个设置的时间hystrix就会调用fallback方法。value的参数为毫秒，默认值为1000ms
                     */
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    //超时开关，当超时后是否触发fallback方法，默认为true。
                    @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                    /**
                     * 熔断器，一个统计窗口时间内（10秒钟）至少请求20次，熔断器才启动。
                     */
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
                    //当在一个时间窗口内出错率超过50%后熔断器自动启动。
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50%"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    //熔断器强制开关，如果设置为true则表示强制打开熔断器，所有请求都会拒绝，默认为false。
                    @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    //该属性是Hystrix用来控制监听到第一个失败调用后打开的时间窗长度，默认是10000ms，即10s。
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
            },
            //threadPoolKey的值则是线程池的标识。如果只是配置了threadPoolKey，那么Hystrix会使用默认配置来初始化该线程池。
            threadPoolKey = "hystrixTest1",
            threadPoolProperties = {
            //线程池参数
            @HystrixProperty(name = "coreSize", value = "1"),
            //该参数表示配置线程值等待队列长度,默认值:-1，建议值:-1表示不等待直接拒绝
            @HystrixProperty(name = "maxQueueSize", value = "10"),
            @HystrixProperty(name = "keepAliveTimeMinutes", value = "1000"),
            //表示等待队列超过阈值后开始拒绝线程请求，默认值为5
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "8")
    })
    public Object hystrixTest1(Long userId) {
        System.out.println(Thread.currentThread().getName());
        return restTemplate.getForEntity("http://user-service/user/web/get?userId=" + userId, String.class).getBody();
    }


    @HystrixCommand(fallbackMethod = "fallback")
    public Object hystrixTest2(Long userId) {
        System.out.println(Thread.currentThread().getName());
        return restTemplate.getForEntity("http://user-service/user/web/get?userId=" + userId, String.class).getBody();
    }


    public Object fallback(Long userId) {
        return "服务熔断，返回默认值";
    }
}
