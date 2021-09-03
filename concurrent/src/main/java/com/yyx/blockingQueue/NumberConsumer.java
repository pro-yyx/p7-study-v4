package com.yyx.blockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class NumberConsumer implements Runnable{

    private BlockingQueue<Integer> queue;

    private final int number;

    public NumberConsumer(BlockingQueue<Integer> queue, int number) {
        this.queue = queue;
        this.number = number;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer numberFromQuene = queue.take();
                if (numberFromQuene.equals(number)) {
                    log.info("获取到终止数据，跳出死循环,数据：{}，{}",Thread.currentThread().getName(),numberFromQuene);
                    break;
                }
                log.info("当前线程：{}，队列出列数据:",Thread.currentThread().getName(),numberFromQuene);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
