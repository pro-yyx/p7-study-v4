package com.yyx.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchRunner {
    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();
        CountDownLatch countDownLatch=new CountDownLatch(2);
        new Thread(new SeeDoctorTak(countDownLatch)).start();
        new Thread(new QueueTask(countDownLatch)).start();
        countDownLatch.await();
        System.out.println("结束："+(System.currentTimeMillis()-now));
    }
}
