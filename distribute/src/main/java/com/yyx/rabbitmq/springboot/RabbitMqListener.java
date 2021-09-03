package com.yyx.rabbitmq.springboot;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author yinyuxin
 * @description 消费者
 * @date 2021/6/4 16:22
 */
@Component
@Slf4j
public class RabbitMqListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @Description 加上注解就可以自动监听message
     * @author yinyuxin
     * @date 2021/6/4
     * @param  * @param message
     * @return void
     */
    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void ListenerQueue(Channel channel, Message message) throws IOException {
        byte[] body = message.getBody();


        //basicAck的两个参数：tag号，第二个  multi 是否签收所有小于tag号的message
        //basicReject的两个参数：tag号，第二个  是否重新加入队列
        //basicNack的三个参数：deliveryTag:该消息的index
        //multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
        //requeue：被拒绝的是否重新入队列
        //nack 和reject的区别：nack可以同时拒绝多条  tag号
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        //nack 和 reject注意  如果没有其他consumer  放回队列后会一直重复消费
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        log.info("接收到message：{}",new String(body));
    }
}
