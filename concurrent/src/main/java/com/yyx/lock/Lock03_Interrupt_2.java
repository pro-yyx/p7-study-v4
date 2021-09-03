package com.yyx.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Lock03_Interrupt_2 {

    private static Lock lock=new ReentrantLock(true);

    private static void reentrantLock() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        boolean flag= false;
        lock.lockInterruptibly();
        log.info("线程：{}，获取到锁",threadName);
        while (true) {
            if (Thread.interrupted()) {
                log.info("检测到线程中断");
                break;
            }
           /* log.info("线程{}的死循环",threadName);*/
        }
        lock.unlock();
        log.info("线程:{},释放了锁",threadName);
    }

    public static void main(String[] args) {
        Thread thread0 = new Thread(()->{
            try {
                reentrantLock();
            } catch (InterruptedException e) {
                log.error("thread0 发生中断异常");
                e.printStackTrace();
            }
        },"thread0");
        thread0.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread2 = new Thread(()->{
            try {
                reentrantLock();
            } catch (InterruptedException e) {
                log.error("thread2 发生中断异常");
                e.printStackTrace();
            }
        },"thread2");
        thread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("准备中断thread2");
        thread2.interrupt();
    }


}
