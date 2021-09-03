package com.yyx.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledThreadPoolExecutorTester {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor=new ScheduledThreadPoolExecutor(5);
       /* ScheduledFuture<?> future = scheduledThreadPoolExecutor.schedule(() -> {
            log.info("我要延迟2s执行");
            return 1;
        }, 2000, TimeUnit.MILLISECONDS);
        future.get();*/
        System.out.println(Long.MAX_VALUE >> 1);
        System.out.println(Long.MAX_VALUE );
    }
}
