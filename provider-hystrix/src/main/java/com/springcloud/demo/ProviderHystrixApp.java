package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 14:47
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class ProviderHystrixApp {
    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrixApp.class, args);
    }
}
