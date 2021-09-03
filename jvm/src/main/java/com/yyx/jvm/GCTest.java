package com.yyx.jvm;

import java.lang.ref.SoftReference;

/**
 * 添加jvm运行参数 ： -XX:+PrintGCDetails
 */
public class GCTest {

    public static void main(String[] args) {
        byte[] allocation1;
        allocation1 = new byte[55000 * 1024];

    }

}
