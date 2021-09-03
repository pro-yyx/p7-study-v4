package com.yyx.jvm;

public class B {
    static {
        System.out.println("***** B static *****");
    }

    public B() {
        System.out.println("***** B init *****");
    }
}
