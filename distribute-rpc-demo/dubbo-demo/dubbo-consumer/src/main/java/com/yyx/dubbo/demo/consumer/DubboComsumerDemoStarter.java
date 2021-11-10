package com.yyx.dubbo.demo.consumer;

import com.yyx.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/20 20:59
 */
@SpringBootApplication
public class DubboComsumerDemoStarter implements WebMvcConfigurer {

    /**
     * 服务消费者，引用provider时 不用管协议，provider使用了多种协议的话，随便哪种协议都可以访问
     * ，consumer会使用轮训的方式选取
     */
    @Reference(version = "asyc")
    private DemoService demoService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DubboComsumerDemoStarter.class);
        //demoservice 是代理对象，这里是因为还没有使用spring容器，所以需要手动获取代理对象
        DemoService demoService = run.getBean(DemoService.class);
        System.out.println(demoService.sayHello("yinyuxin"));

    }

}
