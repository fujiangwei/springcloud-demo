package com.springcloud.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 18:05
 */
@FeignClient(name = "microservice-provider-ha-demo")
public interface ConsumerFeignHaService {

    // @RequestMapping(value = "hello", method = RequestMethod.GET)
    @GetMapping(value = "hello")
    String hello();
}
