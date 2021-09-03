package com.yyx.rocketmq.springboot.transaction;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author yinyuxin
 * @description
 * @date 2021/6/11 11:21
 */
@Component
@Slf4j
public class Producer {
    @Autowired
    private ExtRocketMqTemplate extRocketMqTemplate;

    public void sendBasicMessage(String topic, String message) {
        extRocketMqTemplate.convertAndSend(topic,message);
    }

    public void sendInTrasaction( String message) {
        String[] tags = new String[]{"transaction-tagA","transaction-tagB","transaction-tagC","transaction-tagD"};
        for (int i = 0; i < 10; i++) {
            Message<String> stringMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.TRANSACTION_ID, "trsaction:" + i)
                    //springboot场景下，这里设置的tags发送到事务监听器里后，无法通过tags属性获取到，要么转换为rocketmq自带的messsage，要么
                    //通过destination截取，详情看MyRocketmqTransactionImpl
                    .setHeader(RocketMQHeaders.TAGS, tags[i % tags.length])
                    .setHeader("self-param", "yyx")
                    .build();
            String destination ="transaction-springboot-topic"+":"+tags[i % tags.length];
            //这里发送事务消息时，还是会转换成RocketMQ的Message对象，再调用RocketMQ的API完成事务消息机制。
            SendResult sendResult = extRocketMqTemplate.sendMessageInTransaction(destination, stringMessage,destination);
            log.info("sendInTrasaction()-->sendResult:{}", JSONObject.toJSONString(sendResult));
        }
    }
}
