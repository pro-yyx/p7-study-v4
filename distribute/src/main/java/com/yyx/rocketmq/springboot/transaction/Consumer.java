package com.yyx.rocketmq.springboot.transaction;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author yinyuxin
 * @description
 * @date 2021/6/11 11:34
 */
@Component
@RocketMQMessageListener(consumerGroup = "transaction-springboot-consumer-group",topic = "transaction-springboot-topic")
public class Consumer implements RocketMQListener {
    @Override
    public void onMessage(Object o) {
        System.out.println("Received message : "+ JSONObject.toJSONString(o));
    }
}
