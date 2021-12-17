package com.springcloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 10:52
 */
// @SpringCloudApplication 等效于SpringBootApplication+EnableDiscoveryClient+EnableCircuitBreaker
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class HysTrixApp {
    public static void main(String[] args) {
        SpringApplication.run(HysTrixApp.class, args);
    }

    @Bean
    public RestTemplate initRestTemplate() {
        return new RestTemplate();
    }
}
