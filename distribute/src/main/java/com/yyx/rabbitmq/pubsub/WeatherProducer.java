package com.yyx.rabbitmq.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yyx.rabbitmq.utils.RabbitConstant;
import com.yyx.rabbitmq.utils.RabbitmqUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description 天气producer
 * @date 2021/6/3 15:29
 */
public class WeatherProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        String message = new Scanner(System.in).next();
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER,"",null,message.getBytes());
        channel.close();
        connection.close();
    }
}
