package com.yyx.dubbo.provider.impl;

import com.yyx.dubbo.provider.api.HelloService;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/14 17:53
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello! " + name;
    }
}
