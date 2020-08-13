package com.hong.taskservice.service.impl;

import com.hong.taskservice.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liang
 * @description
 * @date 2020/7/21 16:57
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Override
    public void handleMessage(String messageBody) {
        log.info("业务端开始处理消息");
        /**
         * 维护接口幂等性
         */

    }
}
