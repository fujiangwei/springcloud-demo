package com.springcloud.demo.controller;

import com.springcloud.demo.service.ProviderHystrixService;
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
 * @Date 2021/9/6 16:34
 */
@RestController
public class ProviderHystrixController {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private ProviderHystrixService providerHystrixService;

    @GetMapping(value = "hello")
    public String hello(String type) {
        return "ProviderHystrixController:" + serverPort + "," + providerHystrixService.hello(type);
    }
}
