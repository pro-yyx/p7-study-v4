package com.yyx.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CasABATester {

    private static AtomicInteger atomicInteger=new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
      Thread threada=  new Thread(()->{
            int a = atomicInteger.get();
            log.info("线程:{},修改数据前的atomicInteger值：{}",Thread.currentThread().getName(),a);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicInteger.compareAndSet(a, 2);
            if (b) {
                log.info("线程:{},当前atomicInteger值没变,set后得值：{}", Thread.currentThread().getName(), atomicInteger.get());
            } else {
                log.info("线程:{},当前atomicInteger值改变了,atomicInteger：{}", Thread.currentThread().getName(), atomicInteger.get());
            }
        });

      Thread threadB=  new Thread(()->{
            int i = atomicInteger.incrementAndGet();
            log.info("线程:{},第一次修改数据后的atomicInteger值：{}",Thread.currentThread().getName(),i);
            int i2 = atomicInteger.decrementAndGet();
            log.info("线程:{},第二次修改数据后的atomicInteger值：{}",Thread.currentThread().getName(),i2);

        });
        threada.start();
        Thread.sleep(2000);
        threadB.start();
    }
}
