package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 19:30
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerHaApp {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerHaApp.class, args);
    }
}
