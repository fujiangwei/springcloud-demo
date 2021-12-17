package com.springcloud.demo.controller;

import com.springcloud.demo.service.ConsumerFeignHystrixService;
import com.springcloud.demo.service.ConsumerFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 18:00
 */
@RestController
public class ConsumerFeignController {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private ConsumerFeignService consumerFeignService;

    @Autowired
    private ConsumerFeignHystrixService consumerFeignHystrixService;

    @GetMapping(value = "hello")
    public String hello() {
        String result = consumerFeignService.hello();
        return String.format("ConsumerFeignController:%s,%s", serverPort, result);
    }

    @GetMapping(value = "hello-hystrix")
    public String helloHystrix(String type) {
        String result = consumerFeignHystrixService.hello(type);
        return String.format("ConsumerFeignController:%s,%s", serverPort, result);
    }
}
