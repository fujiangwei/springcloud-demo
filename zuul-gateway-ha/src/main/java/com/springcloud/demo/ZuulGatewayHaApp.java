package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/9/7 9:38
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulGatewayHaApp {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayHaApp.class, args);
    }
}
