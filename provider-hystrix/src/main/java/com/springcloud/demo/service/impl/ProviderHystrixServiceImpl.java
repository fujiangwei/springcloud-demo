package com.springcloud.demo.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springcloud.demo.service.ProviderHystrixService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/9/6 16:38
 */
@Service
public class ProviderHystrixServiceImpl implements ProviderHystrixService {

    @HystrixCommand(fallbackMethod = "helloFallbackMethod")
    public String hello(String type) {
        if (StringUtils.isNotEmpty(type)) {
            return type;
        }

        throw new RuntimeException("type is empty");
    }

    public String helloFallbackMethod(String type) {
        return "helloFallbackMethod type is empty";
    }
}
