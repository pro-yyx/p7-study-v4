package com.yyx.rabbitmq.workqueue;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import com.yyx.rabbitmq.utils.RabbitConstant;
import com.yyx.rabbitmq.utils.RabbitmqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description SMS发送者02
 * @date 2021/6/3 10:12
 */
@Slf4j
public class SMSsender02 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);
        channel.basicConsume(RabbitConstant.QUEUE_SMS, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                SMS sms = JSONObject.parseObject(message, SMS.class);
                log.info("SMSsender02-->短信发送成功：{}",JSONObject.toJSONString(sms));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
