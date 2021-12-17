package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 13:29
 */
@SpringCloudApplication
@EnableEurekaClient
@EnableFeignClients
public class HystrixFeignApp {
    public static void main(String[] args) {
        SpringApplication.run(HystrixFeignApp.class, args);
    }
}
