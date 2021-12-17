package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 17:24
 */
@SpringBootApplication
@EnableEurekaClient
public class ConsumerRibbonHaApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerRibbonHaApp.class, args);
    }

    /**
     * 实例化RestTemplate，用于简化http远程调用
     * @return
     */
    @Bean // 实例化对象
    @LoadBalanced //负载均衡配置
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
