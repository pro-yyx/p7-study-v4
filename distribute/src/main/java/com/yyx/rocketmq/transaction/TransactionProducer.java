package com.yyx.rocketmq.transaction;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yinyuxin
 * @description 事务消息发送者
 * @date 2021/6/10 15:43
 */
@Slf4j
public class TransactionProducer {

    public static void main(String[] args) {
        try {
            TransactionMQProducer producer = new TransactionMQProducer("producer-group-transaction");
            producer.setNamesrvAddr("192.168.89.4:9876;192.168.89.5:9876");
            //线程池用于处理 rollback的回调函数
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 5, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(100), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("producer-group-transaction-thread");
                    return thread;
                }
            });
            producer.setExecutorService(threadPoolExecutor);
            producer.setTransactionListener(new SelfTransactionListenerImpl());
            producer.start();

            String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD"};

            for (int i = 0; i < 10; i++) {
                Message message = new Message("topic-transaction", tags[i % tags.length], "KEY-" + i, ("hello transaction" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(message, null);
                log.info("current thread:{},sendResult:{}", Thread.currentThread().getName(), JSONObject.toJSONString(transactionSendResult));
            }
            producer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
