package com.yyx.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayRunner {

    static int[] valule = new int[]{1, 2, 3};

    static AtomicIntegerArray atomicIntegerArray=new AtomicIntegerArray(valule);

    public static void main(String[] args) {
        //原子修改数组下标0的数值
        atomicIntegerArray.getAndSet(0, 3);
        System.out.println(atomicIntegerArray.get(0));
        System.out.println(atomicIntegerArray.get(0));
        System.out.println(valule[0]);
    }
}
