package com.yyx.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreRunner {


    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(2);
        for (int i = 0; i < 10; i++) {
            new Thread(new Task(semaphore,"thread:"+i)).start();
        }
    }

    static class Task extends Thread {
        Semaphore semaphore;
        public Task(Semaphore semaphore,String threadName) {
            super(threadName);
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
               // Thread.currentThread().interrupt();
                semaphore.acquire();//获取公共资源
                System.out.println(Thread.currentThread().getName() + " acquire semaphore at time:" + System.currentTimeMillis());
                Thread.sleep(2000);
                int a = 10 / 0;
                semaphore.release();//释放公共资源
            } catch (InterruptedException e) {
                System.out.println("线程："+Thread.currentThread().getName()+"没有获取到信号量，触发手动中断");
                Thread.currentThread().interrupt();
            }
        }
    }

}
