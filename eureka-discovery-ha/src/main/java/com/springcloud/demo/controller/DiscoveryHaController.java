package com.springcloud.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/20 14:58
 */
@RestController
public class DiscoveryHaController {
    /**
     * 高可用服务提供应用名
     */
    private static final String HA_PROVIDER_APP_NAME = "microservice-provider-ha-demo";

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "helloHa")
    public String helloHa() {
        List<ServiceInstance> instances = discoveryClient.getInstances(HA_PROVIDER_APP_NAME);
        if (CollectionUtils.isEmpty(instances)) {
            return "DiscoveryHaController: helloHa empty.";
        }
        StringBuilder result = new StringBuilder();
        for (ServiceInstance instance : instances) {
            result.append(String.format("http://%s:%d,%s", instance.getHost(), instance.getPort(), instance.getInstanceId())).append("\n");
        }

        return result.toString();
    }
}
