package com.yyx.rocketmq.springboot.transaction;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yinyuxin
 * @description 业务专属rocketmqtempalte  可多个  用于衔接transactionListener
 * @date 2021/6/11 10:51
 */
@ExtRocketMQTemplateConfiguration(group = "transaction-springboot-producer-group")
@Component
public class ExtRocketMqTemplate extends RocketMQTemplate {

}
