package com.springcloud.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 15:48
 */
@FeignClient(name = "microservice-provider-demo", fallback = HystrixFeignFailServiceImpl.class) // 服务提供者应用名
public interface HystrixFeignService {

    // hello是服务提供者的方法
    @GetMapping(value = "hello")
    String hello();
}
