package com.yyx.rabbitmq.workqueue;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.yyx.rabbitmq.utils.RabbitConstant;
import com.yyx.rabbitmq.utils.RabbitmqUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author yinyuxin
 * @description 发送者
 * @date 2021/6/2 22:34
 */
@Slf4j
public class OrderSystem {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitmqUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false, false, false, null);
        for (int i = 0; i < 100; i++) {
            SMS sms = new SMS("乘客" + i, "您的车票已经购买成功", i+"");
            byte[] message = JSONObject.toJSONString(sms).getBytes();
            channel.basicPublish("",RabbitConstant.QUEUE_SMS,null,message);
        }
        log.info("100条message发送完毕");
        channel.close();
        connection.close();
    }
}
