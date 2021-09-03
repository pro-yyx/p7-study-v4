package com.yyx.countdownlatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class SeeDoctorTak implements Runnable{

    private CountDownLatch countDownLatch;

    public SeeDoctorTak(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
      log.info("开始看医生");
        try {
            Thread.sleep(2000);
            log.info("看医生结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }

    }
}
