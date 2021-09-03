package com.yyx.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description rabbitmq工具包
 * @date 2021/6/2 22:22
 */
public class RabbitmqUtils {

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.89.4");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root123");
        connectionFactory.setVirtualHost("p7_study_v4_01");
        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
