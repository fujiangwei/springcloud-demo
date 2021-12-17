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
public class ConsumerRibbonHaController {

    private static final String HTTP_HA_URL = "http://microservice-provider-ha-demo";

    private static final String HTTP_ZUUL_HA_URL = "http://microservice-zuul-gateway-ha-demo";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping(value = "helloHa")
    public String helloHa() {
        String result = restTemplate.getForObject(HTTP_HA_URL + "/hello", String.class);
        return String.format("ConsumerRibbonHaController:%s,%s", serverPort, result);
    }

    @GetMapping(value = "helloZuulHa")
    public String helloZuulHa() {
        String result = restTemplate.getForObject(HTTP_ZUUL_HA_URL + "/api/provider-ha/hello", String.class);
        return String.format("ConsumerRibbonHaController:%s,%s", serverPort, result);
    }
}
