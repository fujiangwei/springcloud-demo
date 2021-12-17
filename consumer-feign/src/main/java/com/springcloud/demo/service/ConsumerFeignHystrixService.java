package com.springcloud.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 18:05
 */
@FeignClient(name = "microservice-provider-hystrix-demo") // 提供者服务熔断微服务测试
public interface ConsumerFeignHystrixService {

    // @RequestMapping(value = "hello", method = RequestMethod.GET)
    @GetMapping(value = "hello")
    String hello(@RequestParam("type") String type);
}
