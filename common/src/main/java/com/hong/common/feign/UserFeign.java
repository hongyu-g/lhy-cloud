package com.hong.common.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liang
 * @description
 * @date 2020/8/12 14:41
 */
@FeignClient(value = "user-service", fallbackFactory = UserFeignFallbackFactory.class)
@RequestMapping("/user/web")
public interface UserFeign {

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    Object getUser(@RequestParam("userId") Long userId);
}

@Component
class UserFeignFallbackFactory implements FallbackFactory<UserFeign> {
    @Override
    public UserFeign create(Throwable cause) {
        return new UserFeign() {
            @Override
            public Object getUser(Long userId) {
                System.out.println("线程名称：" + Thread.currentThread().getName());
                return "服务降级";
            }
        };
    }
}
