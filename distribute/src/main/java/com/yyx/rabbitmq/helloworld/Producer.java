package com.yyx.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description 消息传递方式-helloworld:不适用exchange
 * @date 2021/6/2 17:26
 */
@Slf4j
public class Producer {

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
        String message = "学习rabbitmq的第一条message";
        //第一个参数：exchange，helloworld模式用不到，发布订阅模式等才会使用
        //第二个参数：queue名称
        //第三个参数：额外的设置属性
        //第四个参数: message的字节数组
        channel.basicPublish("","helloworld",null,message.getBytes());
        channel.close();
        connection.close();
        log.info("数据发送成功");
    }
}
