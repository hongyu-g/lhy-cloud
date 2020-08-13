package com.hong.serviceconfig.config;

import com.hong.serviceconfig.bean.Demo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigTest {

    @Bean
    public Demo getDemo(){
        return new Demo(1,"yu");
    }
}
