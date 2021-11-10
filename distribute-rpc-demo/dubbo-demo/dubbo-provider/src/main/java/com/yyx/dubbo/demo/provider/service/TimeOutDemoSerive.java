package com.yyx.dubbo.demo.provider.service;

import com.yyx.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:23
 */
@Service(version = "timeOut",timeout = 4000)
public class TimeOutDemoSerive implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("执行了timeout服务："+name);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("timeout服务执行结束："+name);
        return "timeout服务:" + name;
    }
}
