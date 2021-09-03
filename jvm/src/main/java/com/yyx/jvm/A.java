package com.yyx.jvm;

public class A {
    static {
        System.out.println("***** A static *****");
    }

    public A() {
        System.out.println("***** A init *****");
    }
}
