package com.yyx.rocketmq.springboot.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinyuxin
 * @description
 * @date 2021/6/11 11:46
 */
@RestController
public class TestController {

    @Autowired
    private Producer producer;

    @GetMapping("/send")
    public void testMessage() {
        producer.sendInTrasaction("测试message");
    }
}
