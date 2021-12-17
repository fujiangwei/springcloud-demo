package com.springcloud.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 14:50
 */
@RestController
public class ProviderController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping(value = "hello")
    public String hello() {
        return "ProviderController:" + serverPort;
    }

}
