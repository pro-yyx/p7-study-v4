package com.yyx.dubbo.demo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/20 20:31
 */
@SpringBootApplication
@EnableDubbo
public class DubboProviderDemoStarter {



    public static void main(String[] args) {
        SpringApplication.run(DubboProviderDemoStarter.class,args);
    }
}
