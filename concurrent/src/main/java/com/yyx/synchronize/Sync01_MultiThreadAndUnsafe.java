package com.yyx.synchronize;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Sync01_MultiThreadAndUnsafe {

    private static int total = 0;

    private static Object object = new Object();

    private static ReentrantLock lock=new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    //当计数器count不为0时，此方法会让当前线程挂起，直到count变成0才会重新激活
                    countDownLatch.await();
                    for (int j = 0; j < 1000; j++) {
                       // total++;
                     /*   synchronized (object) {
                            total++;
                        }*/

                        //reetrantLock statt
                        try {
                            lock.lock();
                            total++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlock();
                        }

                        //reetrantLock end

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(500);
        //count-1
        countDownLatch.countDown();
        Thread.sleep(2000);
        System.out.println(total);
    }
}
