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
 * @description SMS发送者01
 * @date 2021/6/3 10:12
 */
@Slf4j
public class SMSsender01 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);
        //如果不写basicQos，则mq会将所有请求平均发送给所有消费者
        //加上后，mq不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），再从队列中获取一个新的
        //好处：能根据consumer机器性能的不同合理分配消费数量，而不是单纯的轮询平分
        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_SMS, false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                SMS sms = JSONObject.parseObject(message, SMS.class);
                log.info("SMSsender01-->短信发送成功：{}",JSONObject.toJSONString(sms));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
