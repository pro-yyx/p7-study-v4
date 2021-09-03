package com.yyx.kafka.springboot;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yinyuxin
 * @description
 * @date 2021/7/1 15:38
 */
@SpringBootApplication(scanBasePackages = {"com.yyx.kafka.springboot"},exclude = {RedisAutoConfiguration.class, MybatisAutoConfiguration.class,
        DataSourceAutoConfiguration.class, RabbitAutoConfiguration.class, RocketMQAutoConfiguration.class})
public class KafkaSpringBootStarter {
    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringBootStarter.class, args);
    }
}
