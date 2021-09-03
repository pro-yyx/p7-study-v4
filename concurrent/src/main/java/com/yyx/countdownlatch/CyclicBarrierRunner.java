package com.yyx.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class CyclicBarrierRunner implements Runnable{

    private CyclicBarrier cyclicBarrier;

    private int threadIndex;

    public CyclicBarrierRunner(CyclicBarrier cyclicBarrier, int threadIndex) {
        this.cyclicBarrier = cyclicBarrier;
        this.threadIndex = threadIndex;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            log.info("index：{} 到达栅栏 ",threadIndex);
            cyclicBarrier.await();
            log.info("index：{} 冲破栅栏 ",threadIndex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(11, new Runnable() {
            @Override
            public void run() {
               log.info("所有线程到位,开始执行");
            }
        });
        for (int i = 0; i < 10; i++) {
            new Thread(new CyclicBarrierRunner(cyclicBarrier,i)).start();
        }
        try {
            log.info("等待所有线程到位");
            cyclicBarrier.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
