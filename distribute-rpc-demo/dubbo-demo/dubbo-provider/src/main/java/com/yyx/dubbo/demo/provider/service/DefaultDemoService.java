package com.yyx.dubbo.demo.provider.service;

import com.yyx.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:22
 */
@Service(version = "default")
public class DefaultDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        return "default:"+name;
    }
}
