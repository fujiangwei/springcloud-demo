package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/9/7 11:29
 */
@SpringBootApplication
@EnableConfigServer // 启用configServer
@EnableEurekaClient
public class ConfigServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApp.class, args);
    }
}
