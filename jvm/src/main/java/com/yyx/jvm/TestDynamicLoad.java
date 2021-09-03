package com.yyx.jvm;

public class TestDynamicLoad {

    static {
        System.out.println("***** TestDynamicLoad static *****");
    }

    public TestDynamicLoad() {
        System.out.println("***** TestDynamicLoad init *****");
    }

    public static void main(String[] args) {
        A a = new A();
        System.out.println("***** TestDynamicLoad main *****");
        B b = null;
    }
}
