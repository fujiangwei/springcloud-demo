package com.springcloud.demo;

import com.springcloud.demo.filter.MyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/9/6 17:49
 */
@SpringBootApplication
@EnableZuulProxy // 启用Zuul
public class ZuulGatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApp.class, args);
    }

    @Bean
    public MyFilter myFilter() {
        return new MyFilter();
    }
}
