package com.yyx.dubbo.consumer;

import com.yyx.dubbo.framework.ProxyFactory;
import com.yyx.dubbo.provider.api.HelloService;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/15 17:51
 */
public class Consumer {

    public static void main(String[] args) {

        HelloService helloService = (HelloService) ProxyFactory.getProxy(HelloService.class);
        String result = helloService.hello("yyx");
        String protocolName = System.getProperty("protocolName");
        System.out.println("consumer:"+protocolName+":"+result);
    }
}
