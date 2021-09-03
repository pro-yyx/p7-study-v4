package com.yyx.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Lock03_Interrupt {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(()->{
            String threadName = Thread.currentThread().getName();
            for (; ; ) {
               log.info("线程：{}，开始循环",threadName);
                if (Thread.interrupted()) {
                    log.info("线程：{}，被中断",threadName);
                    break;
                }
                log.info("线程：{}，正常执行",threadName);
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        ReentrantLock r=new ReentrantLock();
        r.lock();
        log.info("main 方法执行完毕");
    }
}
