package com.springcloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 17:26
 */
@RestController
public class ConsumerRibbonController {

    private static final String HTTP_URL = "http://microservice-provider-demo";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping(value = "hello")
    public String hello() {
        // 使用ribbon负载实现客户端远程调用时不能用ip:port形式的url进行调用，
        // 而是使用提供者注册到eureka服务的应用名调用，ribbon会将应用名解析出对应的ip:port进行调用
        // String result = restTemplate.getForObject("http://127.0.0.1:8080/" + "/hello", String.class);
        String result = restTemplate.getForObject(HTTP_URL + "/hello", String.class);
        return String.format("ConsumerRibbonController:%s,%s", serverPort, result);
    }
}
