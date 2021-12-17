package com.springcloud.demo.controller;

import com.springcloud.demo.service.HystrixFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 15:45
 */
@RestController
public class HystrixFeignController {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private HystrixFeignService hystrixFeignService;

    @GetMapping(value = "hello")
    public String hello() {
        String result = hystrixFeignService.hello();
        return "HystrixFeignController " + serverPort + "," + result;
    }
}
