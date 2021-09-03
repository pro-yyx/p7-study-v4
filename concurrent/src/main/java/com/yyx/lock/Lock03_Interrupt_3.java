package com.yyx.lock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Lock03_Interrupt_3 {

    public static void main(String[] args) {
        Thread thread1= new Thread(()->{
            log.info("当前线程是:{}",Thread.currentThread().getName());
            while (true) {
                log.info("11"+Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                log.info("22"+Thread.currentThread().isInterrupted());
                log.info("33"+Thread.interrupted());
                log.info("44"+Thread.currentThread().isInterrupted());

                log.info("线程{}的中断状态：{}"+Thread.interrupted());
                try {
                    Thread.sleep(2000);
                    Thread.currentThread().interrupt();
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    log.error("线程{}，捕获到InterruptedException",Thread.currentThread().getName());
                    log.info("线程{}的中断状态2：{}"+Thread.interrupted());
                    Thread.currentThread().interrupt();
                }
                log.info("线程{}的中断状态3：{}"+Thread.interrupted());
                Thread.currentThread().stop();
            }
        });
        thread1.start();
    }
}
