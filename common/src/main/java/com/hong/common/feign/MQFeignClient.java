package com.hong.common.feign;

import com.hong.common.bean.MQBean;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author liang
 * @description
 * @date 2020/7/21 15:01
 */
@FeignClient(value = "service-mq", fallbackFactory = MQFeignClientFallback.class)
@RequestMapping("/mq")
public interface MQFeignClient {

    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    boolean produce(@RequestBody MQBean mqBean);

}

@Component
class MQFeignClientFallback implements FallbackFactory<MQFeignClient> {
    @Override
    public MQFeignClient create(Throwable cause) {
        return new MQFeignClient() {
            @Override
            public boolean produce(MQBean mqBean) {
                return false;
            }
        };
    }
}