package com.yyx.rocketmq.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author yinyuxin
 * @description 事务监听器
 * @date 2021/6/10 15:46
 */
public class SelfTransactionListenerImpl implements TransactionListener {

    /**
     * @Description 在提交完事务消息后执行，返回COMMIT_MESSAGE状态的消息会立即被消费者消费到
     * 返回ROLLBACK_MESSAGE状态的消息会被丢弃
     * 返回UNKONW状态的消息会由broker过一段时间再来回查事务的状态
     * @author yinyuxin
     * @date 2021/6/10
     * @param  * @param message
     * @param o
     * @return org.apache.rocketmq.client.producer.LocalTransactionState
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        String tags = message.getTags();
        if ("TagA".equals(tags)) {
            //消息会立即被消费者消费到
            return LocalTransactionState.COMMIT_MESSAGE;
        } else if ("TagB".equals(tags)) {
            //消息会被丢弃
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {
            return LocalTransactionState.UNKNOW;
        }
    }

    /**
     * @Description 再对UNKONWN状态的消息进行状态回查时执行
     * @author yinyuxin
     * @date 2021/6/10
     * @param  * @param messageExt
     * @return org.apache.rocketmq.client.producer.LocalTransactionState
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String tags = messageExt.getTags();
        if ("TagC".equals(tags)) {
            return LocalTransactionState.COMMIT_MESSAGE;
        } else if ("TagD".equals(tags)) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {
            //多次状态回查后最终被丢弃（默认最多回查15次）
            return LocalTransactionState.UNKNOW;
        }
    }
}
