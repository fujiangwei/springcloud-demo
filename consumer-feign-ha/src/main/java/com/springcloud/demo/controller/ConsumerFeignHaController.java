package com.springcloud.demo.controller;

import com.springcloud.demo.service.ConsumerFeignHaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/19 18:00
 */
@RestController
public class ConsumerFeignHaController {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private ConsumerFeignHaService consumerFeignHaService;

    @GetMapping(value = "helloHa")
    public String helloHa() {
        String result = consumerFeignHaService.hello();
        return String.format("ConsumerFeignHaController:%s,%s", serverPort, result);
    }
}
