package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 18:00
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ConsumerFeignHaApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeignHaApp.class, args);
    }
}
