package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 13:29
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix // 等效@EnableCircuitBreaker
//@EnableCircuitBreaker
public class HystrixRibbonApp {

    public static void main(String[] args) {
        SpringApplication.run(HystrixRibbonApp.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
