package com.yyx.dubbo.demo.provider.service;

import com.yyx.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:23
 */
@Service(version = "rest")
public class RestDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        return "rest:"+name;
    }
}
