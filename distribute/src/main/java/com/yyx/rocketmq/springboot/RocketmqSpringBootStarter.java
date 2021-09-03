package com.yyx.rocketmq.springboot;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yinyuxin
 * @description rocketmq-springboot启动类
 * @date 2021/6/11 10:40
 */
@SpringBootApplication(scanBasePackages = {"com.yyx.rocketmq.springboot"},exclude = {RedisAutoConfiguration.class, MybatisAutoConfiguration.class,
        DataSourceAutoConfiguration.class, RabbitAutoConfiguration.class})
public class RocketmqSpringBootStarter {

    public static void main(String[] args) {
        SpringApplication.run(RocketmqSpringBootStarter.class, args);
    }
}
