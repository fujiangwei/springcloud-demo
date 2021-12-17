package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/9/8 16:53
 */
@SpringBootApplication
@EnableEurekaServer
public class ConfigEurekaServerApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigEurekaServerApp.class, args);
    }
}
