package com.springcloud.demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 11:04
 */
@RestController
public class HystrixController {

    @Value("${server.port}")
    private int serverPort;

    private static final String PROVIDER_SER_NAME = "microservice-provider-demo";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "hello")
    @HystrixCommand(fallbackMethod = "helloFallbackMethod")
    public String hello() {
        List<ServiceInstance> instances = discoveryClient.getInstances(PROVIDER_SER_NAME);
        if (CollectionUtils.isEmpty(instances)) {
            return "HystrixController empty.";
        }

        ServiceInstance instance = instances.get(0);
        String result = restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/hello", String.class);
        return "HystrixController " + serverPort + "," + result;
    }

    public String helloFallbackMethod() {
        return "HystrixController helloFallbackMethod";
    }

    @GetMapping(value = "hi")
    @HystrixCommand(fallbackMethod = "hiFallbackMethod", // 请求失败降级回调方法，值为方法名，不需要括号
            commandProperties = { // 针对单个方法的配置
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //开启熔断器，可不加默认为true
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),//请求错误超过50%，开启熔断器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//一个周期(十秒)内超过10个请求才进行进行容错率判断
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")//开启熔断器后过10秒再尝试访问
    })
    public String hi() {
        List<ServiceInstance> instances = discoveryClient.getInstances(PROVIDER_SER_NAME);
        if (CollectionUtils.isEmpty(instances)) {
            return "HystrixController empty.";
        }

        ServiceInstance instance = instances.get(0);
        String result = restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/hello", String.class);
        return "HystrixController hi " + serverPort + "," + result;
    }

    public String hiFallbackMethod() {
        return "HystrixController hiFallbackMethod";
    }

    @GetMapping(value = "hiCommandKey")
    @HystrixCommand(fallbackMethod = "hiCommandKeyFallbackMethod", // 请求失败降级回调方法，值为方法名，不需要括号
            commandKey = "commonCommand" // 基于hystrix commandKey，定义一个commandKey，和配置文件来进行全局配置，不设置默认取方法名为commandKey
    )
    public String hiCommandKey() {
        List<ServiceInstance> instances = discoveryClient.getInstances(PROVIDER_SER_NAME);
        if (CollectionUtils.isEmpty(instances)) {
            return "HystrixController empty.";
        }

        ServiceInstance instance = instances.get(0);
        String result = restTemplate.getForObject("http://" + instance.getHost() + ":" + instance.getPort() + "/hello", String.class);
        return "HystrixController hiCommandKey " + serverPort + "," + result;
    }

    public String hiCommandKeyFallbackMethod() {
        return "HystrixController hiCommandKeyFallbackMethod";
    }
}
