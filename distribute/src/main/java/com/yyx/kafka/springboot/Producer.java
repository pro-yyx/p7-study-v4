package com.yyx.kafka.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinyuxin
 * @description
 * @date 2021/7/1 15:16
 */
@RestController
public class Producer {

    private final static String TOPIC_NAME = "my-replicated-topic-springboot";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public void send() {
        kafkaTemplate.send(TOPIC_NAME, 0,"key", "this is springboot kafka first message");
    }
}
