package com.springcloud.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 14:42
 */
@Service
// 默认的兜底方法
@DefaultProperties(defaultFallback = "defaultFallbackMethod")
public class HystrixRibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallbackMethod")
    // @HystrixCommand() // 使用默认的
    public String hello(final String reqUrl) {
        return restTemplate.getForObject(reqUrl, String.class);
    }

    // 回退方法名需要和调用的方法一致，不然找不到回退方法
    public String helloFallbackMethod(final String reqUrl) {
        return "HystrixRibbonController helloFallbackMethod(回退方法名需要和调用的方法一致) " + reqUrl;
    }

    @HystrixCommand() // 未指定兜底方法时使用全局配置的默认回退方法
    public String hi(final String reqUrl) {
        return restTemplate.getForObject(reqUrl, String.class);
    }

    public String defaultFallbackMethod() {
        return "HystrixRibbonController defaultFallbackMethod";
    }
}
