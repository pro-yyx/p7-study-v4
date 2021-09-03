package com.yyx.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AutomicInt {

    private static AtomicInteger atomicInteger=new AtomicInteger();
    //private static volatile int atomicInteger;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
               atomicInteger.incrementAndGet();
                //atomicInteger++;
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("atomicInteger的值："+atomicInteger);
    }
}
