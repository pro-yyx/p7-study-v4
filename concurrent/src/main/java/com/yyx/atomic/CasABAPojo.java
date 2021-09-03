package com.yyx.atomic;

public class CasABAPojo {

    private volatile String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
