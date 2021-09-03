package com.yyx.rabbitmq.pubsub;

import com.rabbitmq.client.*;
import com.yyx.rabbitmq.utils.RabbitConstant;
import com.yyx.rabbitmq.utils.RabbitmqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description 新浪消费者
 * @date 2021/6/3 15:35
 */
@Slf4j
public class SinaConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel=connection.createChannel();
        //申明队列信息
        channel.queueDeclare(RabbitConstant.QUEUE_WEATHER_SINA, false, false, false, null);
        //队列绑定交换机
        //参数：1.队列名 2.交换机名  3.route key（routing 模式）
        channel.queueBind(RabbitConstant.QUEUE_WEATHER_SINA, RabbitConstant.EXCHANGE_WEATHER, "");
        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_WEATHER_SINA,false,new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("新浪收到天气预报:{}", new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
