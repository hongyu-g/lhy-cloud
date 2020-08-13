package com.hong.userservice.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liang
 * @description
 * @date 2020/7/24 18:48
 */
@Configuration
public class Config {

    //@Bean(name = "person")
    public Person getPerson(){
        return new Person();
    }
}
