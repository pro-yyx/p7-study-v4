package com.yyx.blockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NumberGeneratorMain {

    public static void main(String[] args) throws InterruptedException {
        int queueSize = 10;
        int number = Integer.MAX_VALUE;
        int number_producer = 16;
        int number_consumer = Runtime.getRuntime().availableProcessors();
        int numberPerProducer = number_consumer / number_producer;
        int mod = number_consumer % number_producer;

        BlockingQueue queue=new LinkedBlockingQueue<>(queueSize);
        for (int i = 1; i < number_producer; i++) {
            new Thread(new NumberProducer(queue, number, numberPerProducer)).start();
        }
        for (int j = 1; j < number_consumer; j++) {
            new Thread(new NumberConsumer(queue, number)).start();

        }
        Thread.sleep(5000);
        new Thread(new NumberProducer(queue, number,numberPerProducer+mod)).start();
    }
}
