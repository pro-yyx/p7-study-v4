package com.yyx.kafka.quickstart;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author yinyuxin
 * @description
 * @date 2021/6/18 11:21
 */
public class Consumer {

    private static final String TOPIC_NAME = "my-replicated-topic";

    private static final String CONSUMER_GROUP = "testConsumerGroup";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.89.4:9092,192.168.89.5:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        //是否自动提交offset，默认为ture
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        //自动提交offset的间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //当消费topic的是一个新的消费组，或者指定offset的消费方式，offset不存在，那么该如何消费
        //latest（默认）：只消费自己启动之后发送到topic的消息
        //earliest：第一次从头开始消费，以后按照offset记录继续消费，这个需要区别于 consumer.seekToBeinning(每次都重头消费)
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //consumer 发送给broker心跳的间隔时间，如果此时有rebalance发生会通过心跳响应将rebalance方案下发给consumer
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
        //broker多久感知不到consumer心跳就认为他故障了，会将其剔出消费组，对应的partition也会被重新分配给其他consumer，默认是10秒
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);
        //一次poll最大拉取消息的条数，如果消费者处理速度很快，可以设置大点
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        //如果两次poll操作间隔超过了这个时间，broker就会认为这个consumer处理能力太弱，会将其提出消费组，将分区分配给别的consumer消费
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        //消费指定分区
        //consumer.assign(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));

        //消息回溯消费：从头开始消费
        //consumer.assign(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));
        //consumer.seekToBeginning(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));

        //指定offset消费
        //consumer.assign(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));
        //consumer.seek(new TopicPartition(TOPIC_NAME, 0),10);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("收到消息：record:"+JSONObject.toJSONString(record));
            }
            if (records.count() > 0) {
                //手动同步提交offset，当前线程会阻塞知道offset提交成功
                //一般使用同步提交，因为提交之后一般也没什么逻辑代码了
               // consumer.commitSync();

                //手动异步提交offset，当前线程提交offset不会阻塞，可以继续处理后面的程序逻辑，异步提交后会回调以下方法
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                        if (e != null) {
                            System.out.println("Commit failed for " + map);
                            System.out.println("Commit failed exception: " + e.getStackTrace());
                        }
                    }
                });
            }
        }
    }
}
