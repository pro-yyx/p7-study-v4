package com.yyx.rabbitmq.springboot;

import com.rabbitmq.client.ConfirmListener;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yinyuxin
 * @description rabbitmq配置类
 * @date 2021/6/4 15:00
 */
@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "springboot-rabbit-exchange-01";

    public static final String QUEUE_NAME = "springboot-rabbit-queue-01";

   /**
    * @Description 申明交换机exchange
    * @author yinyuxin
    * @date 2021/6/4
    * @param  * @param
    * @return org.springframework.amqp.core.Exchange
    */
   @Bean("bootExchange01")
    public Exchange initExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * @Description 申明queue队列
     * @author yinyuxin
     * @date 2021/6/4
     * @param  * @param
     * @return org.springframework.amqp.core.Queue
     */
    @Bean("bootQueue01")
    public Queue initQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * @Description
     * @author yinyuxin
     * @date 2021/6/4
     * @param exchange
     * @param queue
     * @return void
     */
    @Bean("bootBinding01")
    public Binding bindingExchangeAndQueue(@Qualifier("bootExchange01") Exchange exchange,@Qualifier("bootQueue01") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

}
