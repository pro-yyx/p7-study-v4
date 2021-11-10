package com.yyx.dubbo.demo.provider.service;

import com.yyx.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:21
 */
// version 标签也是服务名的区别方式  ip：port/demoserviceasyc
@Service(version = "asyc")
public class AsyncDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        return "asyc:"+name;
    }

    @Override
    public CompletableFuture<String> sayHelloAsync(String name) {
        System.out.println("执行了异步服务："+name);
        return CompletableFuture.supplyAsync(() -> sayHello(name));
    }
}
