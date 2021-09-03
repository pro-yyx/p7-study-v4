package com.yyx.jvm;

public class Math {
    public static final int initData = 600;

   // public static User user = new User();

    public int compute() {   // 一个方法对应一个栈帧内存空间
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        Math math = new Math();
        int compute = math.compute();
        System.out.println(compute);
    }
}
