package com.hong.userservice.hystrix;

import com.hong.userservice.bean.User;
import com.hong.userservice.service.UserService;
import com.netflix.hystrix.*;

/**
 * @author liang
 * @description
 * @date 2020/8/13 11:07
 */
public class QueryUserCommand extends HystrixCommand<User> {


    private UserService userService;


    public QueryUserCommand(UserService userService) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("getUser"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(10)//至少有10个请求，熔断器才进行错误率的计算
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)//熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
                        .withCircuitBreakerErrorThresholdPercentage(50)//错误率达到50开启熔断保护
                        .withExecutionTimeoutEnabled(true))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties
                        .Setter().withCoreSize(10)));
        this.userService = userService;
    }

    @Override
    protected User run() {
        return userService.getUser(34L);
    }

    @Override
    protected User getFallback() {
        return new User();
    }
}
