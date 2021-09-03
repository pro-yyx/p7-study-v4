package com.yyx.jmm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeVisibility {
    private  static volatile boolean initFlag = false;

    private  static int counter = 0;

    public static void refresh() {
        log.info("refresh data ...");
        initFlag = true;
        log.info("refresh data success...");
    }

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            while (!initFlag) {

            }
            log.info("线程：" + Thread.currentThread().getName() + ",当前线程嗅探到initFlag的状态的改变", "threadA");
        }, "threadA");
        threadA.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadB =new Thread(() -> {
            refresh();
        },"threadB");
        threadB.start();

    }
}
