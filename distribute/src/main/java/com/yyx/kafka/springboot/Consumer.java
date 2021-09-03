package com.yyx.kafka.springboot;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author yinyuxin
 * @description
 * @date 2021/7/1 15:19
 */
@Component
public class Consumer {

/*    @KafkaListener(groupId = "springbootConsumerGroup",topicPartitions = {@TopicPartition(topic = "my‐replicated‐topic-springboot",partitions = {"0","1"}),
        @TopicPartition(topic = "topic2",partitions = "0",partitionOffsets = @PartitionOffset(partition = "1",initialOffset = "100"))},
    concurrency = "6") concurrenc就是同组下的消费者个数，就是并发消费数，必须小于等于分区数
    */
    @KafkaListener(groupId = "springbootConsumerGroup",topics = "my-replicated-topic-springboot")
    public void listenSpringBootTopic(ConsumerRecord<String,String> record, Acknowledgment ack) {
        System.out.println("springbootConsumerGroup get message:"+JSONObject.toJSONString(record));
        //手动提交offset
        String value = record.value();
         System.out.println(value);
         System.out.println(record);
        ack.acknowledge();
    }

   /*//配置多个消费组
   @KafkaListener(topics = "my-replicated-topic-springboot", groupId = "springbootConsumerGroup2")
   public void listenTulingGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
       System.out.println("springbootConsumerGroup2 get message:"+JSONObject.toJSONString(record));
       //手动提交offset
       ack.acknowledge();
    }*/
}
