package com.yyx.shardingsphere;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

/**
 * @author yinyuxin
 * @description
 * @date 2021/7/22 18:31
 */
@MapperScan("com.yyx.shardingsphere.mapper")
@SpringBootApplication(scanBasePackages = {"com.yyx.shardingsphere"},exclude = {RedisAutoConfiguration.class,
         RabbitAutoConfiguration.class, RocketMQAutoConfiguration.class, KafkaAutoConfiguration.class})
public class ShardingJDBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJDBCApplication.class, args);
    }
}
