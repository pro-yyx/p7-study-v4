package com.yyx.jmm;

public class Jmm06_DoubleCheckLock {

    private static Jmm06_DoubleCheckLock instance;

    /*private static volatile Jmm06_DoubleCheckLock instance;*/

    private Jmm06_DoubleCheckLock() {

    }

    public static Jmm06_DoubleCheckLock getInstance() {
        //第一次检测
        if (instance == null) {
            //同步
            synchronized (Jmm06_DoubleCheckLock.class) {
                //双重校验的原因：多线程环境下，可能出现多条线程都通过第一次null判断，但synchronized只允许
                //一条线程进入，当该线程完成new初始化并释放资源后，第二次null判断可避免其他线程进入重复创建实例；
                if (instance == null) {
                    //多线程环境下可能会出现问题的地方
                    //对象的初始化过程可分解为三部(非原子操作)：1、分配对象内存空间
                    //2:、初始化对象 ， 3、对象指向内存空间（此时instance才不为null）， 其中2和3 可指令重排，并发环境下会导致instance非空判断失真、
                    //解决办法:给instance 加 volatile修饰。禁止该对象被指令重排
                    instance = new Jmm06_DoubleCheckLock();
                }
            }
        }
        return instance;
    }
}
