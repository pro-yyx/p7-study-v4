package com.yyx.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Lock01_LockSupport {

    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(()->{
            String threadName = Thread.currentThread().getName();
            for (; ; ) {
                log.info("准备park当前线程，name：{}",threadName);
                LockSupport.park();
                Thread.interrupted();
                log.info("当前线程：{},已经被唤醒",threadName);
            }
        });
        thread0.start();
        Thread.sleep(5000);
        LockSupport.unpark(thread0);
        thread0.interrupt();
        log.info("准备唤醒{}线程",thread0.getName());
    }
}
