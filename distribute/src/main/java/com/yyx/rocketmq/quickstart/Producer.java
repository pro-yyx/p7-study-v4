package com.yyx.rocketmq.quickstart;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author yinyuxin
 * @description 基础模式producer
 * @date 2021/6/9 16:36
 */
@Slf4j
public class Producer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        DefaultMQProducer producer=new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.89.4:9876;192.168.89.5:9876");

        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message=new Message("topic-quickstart","tags-quickstart" ,"key-quickstart",
                    "helloworld".getBytes(RemotingHelper.DEFAULT_CHARSET));
            //单向发送，不管消息发送到broker的状态
            producer.sendOneway(message);
            //同步发送
            //SendResult sendResult = producer.send(message);
            //log.info("sendRulst:{}", JSONObject.toJSONString(sendResult));
            //异步发送
        }
        log.info("quickstart 数据发送完毕");
        producer.shutdown();
    }
}
