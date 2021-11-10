package com.yyx.rabbitmq.springboot;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yinyuxin
 * @description spring-boot-rabbitmq启动类
 * @date 2021/6/4 15:16
 */
@SpringBootApplication(scanBasePackages = {"com.yyx.rabbitmq.springboot"},exclude = {RedisRepositoriesAutoConfiguration.class,
        MybatisAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class SpringbootRabbitMqStarter {
    @Value("${server.port}")
    private static String name;

    public static void main(String[] args) {

        System.out.println("ssssssss----:"+name);
        SpringApplication.run(SpringbootRabbitMqStarter.class, args);
    }
}
