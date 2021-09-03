package com.yyx.rocketmq.springboot.transaction;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yinyuxin
 * @description: 事务消息监听器
 * 关于@RocketMQTransactionListener 这个注解，有点奇怪。2.0.4版本中，是需要指定txProducerGroup指向一个消息发送者组。不同的组可以有不同的事务消息逻辑。
 * 但是到了2.1.1版本，只能指定rocketMQTemplateBeanMame，也就是说如果你有多个发送者组需要有不同的事务消息逻辑，那就需要定义多个RocketMQTemplate。
 * 而且这个版本中，虽然重现了我们在原生API中的事务消息逻辑，但是测试过程中还是发现一些奇怪的特性，用的时候要注意点。
 * @date 2021/6/11 10:59
 */
//@RocketMQTransactionListener(txProducerGroup = "springBootGroup2")
@RocketMQTransactionListener(rocketMQTemplateBeanName = "extRocketMqTemplate")
@Slf4j
public class MyRocketmqTransactionImpl implements RocketMQLocalTransactionListener {

    private ConcurrentHashMap<String, Message> localTrans = new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        Object transactionId = message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TRANSACTION_ID);
        String destination = o.toString();
        localTrans.put(transactionId.toString(), message);
        log.info("executeLocalTransaction()-->message:{},o:{}", JSONObject.toJSONString(message),JSONObject.toJSONString(o));
        //获取tags
        org.apache.rocketmq.common.message.Message message1 = RocketMQUtil.convertToRocketMessage(new StringMessageConverter(), "UTF-8", destination, message);
        String tags = message1.getTags();
        if ("transaction-tagA".equals(tags)) {
            return RocketMQLocalTransactionState.COMMIT;
        } else if ("transaction-tagB".equals(tags)) {
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String transId = message.getHeaders().get(RocketMQHeaders.PREFIX+RocketMQHeaders.TRANSACTION_ID).toString();
        Message originalMessage = localTrans.get(transId);
        log.info("checkLocalTransaction()-->message in localTrans:{}",JSONObject.toJSONString(originalMessage));
        //获取tags时，自定义的RocketMQHeaders.TAGS拿不到，但是框架会封装成一个带RocketMQHeaders.PREFIX的属性
        //String tags = msg.getHeaders().get(RocketMQHeaders.TAGS).toString();
        String tags = message.getHeaders().get(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS).toString();
        if(StringUtils.contains(tags,"transaction-tagC")){
            return RocketMQLocalTransactionState.COMMIT;
        }else if(StringUtils.contains(tags,"transaction-tagD")){
            return RocketMQLocalTransactionState.ROLLBACK;
        }else{
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
