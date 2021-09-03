package com.yyx.jvm;

import java.util.HashSet;
import java.util.Set;

public class Arthas {

    private static Set<Object> hashSet = new HashSet<>();

    public static void main(String[] args) {
        //1：模拟cpu高占用
        highCpu();
        //2：模拟死锁
        threadDeadLock();
        //3：模拟不断像set里添加数据
        pushSet();
    }

    private static void highCpu() {
        new Thread(()->{
            while (true) {

            }
        }).start();
    }

    private static void threadDeadLock() {
        Object LockA=new Object();
        Object LockB=new Object();
        Thread threadA=new Thread(()->{
            synchronized (LockA) {
                System.out.println("threadA get the LockA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadA waiting the LockB");
                synchronized (LockB) {
                    System.out.println("threadA get the LockB");
                }
            }
        });

        Thread threadB=new Thread(()->{
            synchronized (LockB) {
                System.out.println("threadB get the LockB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadB waiting the LockB");
                synchronized (LockA) {
                    System.out.println("threadB get the LockA");
                }
            }
        });
        threadA.start();
        threadB.start();
    }

    private static void pushSet() {
        new Thread(()->{
            int count = 0;
            while (true) {
                hashSet.add(count);
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
