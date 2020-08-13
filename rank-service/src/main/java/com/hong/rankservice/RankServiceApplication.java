package com.hong.rankservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hong")
@MapperScan("com.hong.rankservice.dao")
public class RankServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankServiceApplication.class, args);
    }

}
