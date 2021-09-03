package com.yyx.rocketmq.transaction;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import sun.rmi.runtime.Log;

import java.util.List;

/**
 * @author yinyuxin
 * @description 事务消息消费者
 * @date 2021/6/10 16:33
 */
@Slf4j
public class TransactionConsumer {

    public static void main(String[] args) {

        try {
            DefaultMQPushConsumer consumer=new DefaultMQPushConsumer("consumer-group-transaction");
            consumer.setNamesrvAddr("192.168.89.4:9876;192.168.89.5:9876");
            consumer.subscribe("topic-transaction","TagA || TagC");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    log.info("接收到消息：{}", JSONObject.toJSONString(list));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
