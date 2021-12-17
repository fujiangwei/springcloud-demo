package com.springcloud.demo.controller;

import com.springcloud.demo.service.HystrixRibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 13:41
 */
@RestController
public class HystrixRibbonController {

    @Value("${server.port}")
    private int serverPort;

    private static final String PROVIDER_SER_NAME = "microservice-provider-demo";

    @Autowired
    private HystrixRibbonService hystrixRibbonService;

    @GetMapping(value = "hello")
    public String hello() {
        String result = hystrixRibbonService.hello("http://" + PROVIDER_SER_NAME + "/hello");
        return "HystrixRibbonController hello " + serverPort + "," + result;
    }

    @GetMapping(value = "hi")
    public String hi() {
        String result = hystrixRibbonService.hi("http://" + PROVIDER_SER_NAME + "/hello");
        return "HystrixRibbonController hi " + serverPort + "," + result;
    }
}
