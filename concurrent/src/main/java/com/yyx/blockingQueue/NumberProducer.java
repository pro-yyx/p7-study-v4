package com.yyx.blockingQueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class NumberProducer implements Runnable{

    private BlockingQueue<Integer> queue;

    private int number;

    private int numberPerProducer;

    public NumberProducer(BlockingQueue<Integer> queue, int number, int numberPerProducer) {
        this.queue = queue;
        this.number = number;
        this.numberPerProducer = numberPerProducer;
    }

    @Override
    public void run() {
        try {
            generateNumber();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void generateNumber() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            int number = ThreadLocalRandom.current().nextInt(100);
            queue.put(number);
            log.info("producer 往 queue中 塞入positiveNumber，thread：{}，数据{}",Thread.currentThread().getName(),number);
        }
    }
}
