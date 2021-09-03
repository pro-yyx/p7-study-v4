package com.yyx.executor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadPoolExecutorTester {

    private static ConcurrentHashMap<String,Runnable> failedRunableMap = new ConcurrentHashMap<>();
    private static CountDownLatch countDownLatch=new CountDownLatch(20);

    public static void main(String[] args) throws InterruptedException{

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5,
                10,
                5000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),
                new MyThreadFactory(),
                new MyRejectPolicy());
        for (int i = 0; i < 20; i++) {
            MyRunnable myRunnable = new MyRunnable(i+"");
            threadPoolExecutor.execute(myRunnable);
        }
        countDownLatch.await();
        log.info("线程池执行完毕，failedRunableMap:{}", JSONObject.toJSONString(failedRunableMap));
        Thread.sleep(2000);
        log.info("继续交由threadPoolExecutor 执行");
        while (!failedRunableMap.isEmpty()) {
            AtomicInteger atomicInteger = new AtomicInteger(2);
            for (Runnable runnable : failedRunableMap.values()) {
                threadPoolExecutor.execute(runnable);
                Thread.sleep(3000);
                log.info("线程池第{}次执行完毕，failedRunableMap:{}", atomicInteger.getAndIncrement(),JSONObject.toJSONString(failedRunableMap));
            }
        }
        log.info("线程池最终执行完毕，failedRunableMap:{}", JSONObject.toJSONString(failedRunableMap));
        threadPoolExecutor.shutdown();
        threadPoolExecutor = null;
    }

    static class MyThreadFactory implements ThreadFactory {
        private final static AtomicInteger atomicInteger=new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread=new Thread(r,"threadNum:"+atomicInteger.getAndIncrement());
            log.info("线程:{}创建成功",thread.getName());
            return thread;
        }
    }

    static class MyRejectPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.error("executor 饱和,添加任务失败,Runnable:{}",JSONObject.toJSONString(r));
            failedRunableMap.put(r.toString(), r);
            countDownLatch.countDown();
        }
    }

    static class MyRunnable implements Runnable {
        private String name;
        public MyRunnable(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            try {
                log.info("线程：{}正在执行任务{}",Thread.currentThread().getName(),this.toString() );
                Thread.sleep(3000); //让任务执行慢点
                failedRunableMap.remove(toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }
        public String getName() {
            return name;
        }
        @Override
        public String toString() {
            return "MyRunnable [name=" + name + "]";
        }
    }


}
