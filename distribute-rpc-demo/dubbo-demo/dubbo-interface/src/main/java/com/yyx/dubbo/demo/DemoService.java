package com.yyx.dubbo.demo;

import java.util.concurrent.CompletableFuture;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:16
 */
public interface DemoService {

    //同步调用方法
    String sayHello(String name);

    //异步调用方法
    default CompletableFuture<String> sayHelloAsync(String name) {
        return null;
    }

    //添加回调
    default String sayHello(String name,String key,DemoServiceListener listener) {
        return null;
    }
}
