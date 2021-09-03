package com.yyx.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Slf4j
public class CasABATester2 {
/*
    private static AtomicReferenceArray<CasABAPojo> atomic =new AtomicReferenceArray<>(3);

    public static void main(String[] args) throws InterruptedException {

        CasABAPojo casABAPojo0 = new CasABAPojo();
        casABAPojo0.setName("00");
        CasABAPojo casABAPojo1 = new CasABAPojo();
        casABAPojo0.setName("11");
        CasABAPojo casABAPojo2 = new CasABAPojo();
        casABAPojo0.setName("22");
        atomic.set(0,casABAPojo0);
        atomic.set(1,casABAPojo1);
        atomic.set(2,casABAPojo2);

        Thread threada=  new Thread(()->{
            CasABAPojo a = atomic.get(0);
            log.info("线程:{},修改数据前的值：{}",Thread.currentThread().getName(),a.getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a.setName("33");
            boolean b = atomic.compareAndSet(0, a,a);
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
    }*/
}
