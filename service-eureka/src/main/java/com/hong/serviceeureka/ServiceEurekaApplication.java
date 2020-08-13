package com.hong.serviceeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 开启Eureka服务
 */
@SpringBootApplication
@EnableEurekaServer
public class ServiceEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceEurekaApplication.class, args);
    }

}
