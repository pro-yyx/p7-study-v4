package com.yyx.netty.netty.directbuffer;

import java.nio.ByteBuffer;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/7 13:52
 */
public class DirectMemoryOOM {

    public static void heapAccess() {
        long startTime = System.currentTimeMillis();
        ByteBuffer allocate = ByteBuffer.allocate(2000);
        for (int i = 0; i <100000; i++) {
            for (int j = 0; j < 200; j++) {
                allocate.putInt(j);
            }
            //读写模式切换
            allocate.flip();
            for (int j = 0; j < 200; j++) {
                allocate.getInt();
            }
            allocate.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("堆内存访问:" + (endTime - startTime) + "ms");
    }

    public static void directAccess() {
        long startTime = System.currentTimeMillis();
        //分配直接内存
        ByteBuffer allocate = ByteBuffer.allocateDirect(2000);
        for (int i = 0; i <100000; i++) {
            for (int j = 0; j < 200; j++) {
                allocate.putInt(j);
            }
            //读写模式切换
            allocate.flip();
            for (int j = 0; j < 200; j++) {
                allocate.getInt();
            }
            allocate.clear();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("直接内存访问:" + (endTime - startTime) + "ms");
    }

    public static void heapAllocate() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            ByteBuffer.allocate(200);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("堆内存申请:" + (endTime - startTime) + "ms");
    }

    public static void directAllocate() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            ByteBuffer.allocate(200);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("直接内存申请:" + (endTime - startTime) + "ms");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            heapAccess();
            directAccess();
        }
        System.out.println("end");
        for (int i = 0; i < 10; i++) {
            heapAllocate();
            directAllocate();
        }
    }
}
