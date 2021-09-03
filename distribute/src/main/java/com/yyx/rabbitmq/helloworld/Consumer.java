package com.yyx.rabbitmq.helloworld;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description helloworld模式的消费者
 * @date 2021/6/2 17:52
 */
@Slf4j
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.89.4");
        //rabbitmq的默认端口号，15672是管理界面的端口号
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root123");
        connectionFactory.setVirtualHost("/p7_study_v4_01");
        //获取TCP长链接
        Connection connection = connectionFactory.newConnection();
        //创建通信“channel”，相当于tcp中的虚拟连接
        Channel channel = connection.createChannel();
        //如果在server端没有配置这个queue,会自动创建，不会报错
        //第一个参数：queue名称
        //第二个参数：是否持久化，false对应不持久化数据，mq停掉后会丢失数据
        //第三个参数：队列是否私有化，false代表所有的消费者都可以访问，true代表只有第一次拥有他的消费者才能一直使用
        //第四个参数：是否自动删除，false代表连接停掉后不自动删除掉这个队列
        //第五个参数：额外的设置属性
        channel.queueDeclare("helloworld", false, false, false, null);
        //第一个参数：queue名称
        //第二个参数：是否自动确认收到消息，false代表手动编程来确认，这是mq的推荐做法
        //第三个参数: 要传入defaultConsumer的实现类
        channel.basicConsume("helloworld", false,new Reciver(channel) );

        log.info("数据消费成功");
    }

    static class Reciver extends DefaultConsumer {
        private Channel channel;
        //重写构造函数，channel通道对象需要从外层传入，在handleDelivery中被使用
        public Reciver(Channel channel) {
            super(channel);
            this.channel = channel;
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String message = new String(body);
            log.info("consumer接收到的消息:{}",message);
            log.info("consumer接收到的消息的tagID:{}",envelope.getDeliveryTag());
            //false代表只确认当前的消息，为true时代表签收该消费者所有未签收的消息
            channel.basicAck(envelope.getDeliveryTag(),false);
        }
    }

}
