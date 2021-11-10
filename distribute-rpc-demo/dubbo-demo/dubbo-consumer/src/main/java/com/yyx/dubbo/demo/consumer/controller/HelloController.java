package com.yyx.dubbo.demo.consumer.controller;

import com.yyx.dubbo.demo.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/20 21:10
 */
public class HelloController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/consumer/user/say")
    public String sayHello() {
        return demoService.sayHello("yinyuxin");
    }
}
