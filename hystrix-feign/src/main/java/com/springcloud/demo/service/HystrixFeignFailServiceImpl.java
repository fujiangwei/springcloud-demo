package com.springcloud.demo.service;

import org.springframework.stereotype.Component;

/**
 * @author
 * @Description: 文件描述
 * @Author:
 * @Version:
 * @Date 2021/8/23 16:03
 */
@Component
public class HystrixFeignFailServiceImpl implements HystrixFeignService {

    public String hello() {
        return "HystrixFeignFailServiceImpl fail.";
    }
}
