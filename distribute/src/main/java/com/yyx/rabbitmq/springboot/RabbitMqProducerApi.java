package com.yyx.rabbitmq.springboot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yinyuxin
 * @description 生产者
 * @date 2021/6/4 16:28
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitMqProducerApi implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(path = "/send",method = RequestMethod.GET)
    public void sendMessage() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,"boot.test","这是测试数据");
      //  rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,"boot222.test","这是测试数据");
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        //System.out.println("消息发送成功,发送ack确认,id="+correlationData.getId());
        System.out.println("string:{}"+ s);
        if (b){
            System.out.println("发送成功");
        }else {
            System.out.println("发送失败");
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("消息丢失, 没有投递成功:"+new String(message.getBody()));
    }
}
