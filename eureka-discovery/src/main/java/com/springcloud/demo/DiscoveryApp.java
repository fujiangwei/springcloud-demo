package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/20 14:58
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryApp {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryApp.class, args);
    }
}
