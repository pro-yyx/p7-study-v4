package com.yyx.rocketmq.springboot.testDomain;

/**
 * @author ：yinyuxin
 * @date ：Created in 2020/11/28
 * @description:
 **/

public class ProductWithPayload<T> {
    private String productName;
    private T payload;

    public ProductWithPayload() {
    }

    public ProductWithPayload(String productName, T payload) {
        this.productName = productName;
        this.payload = payload;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override public String toString() {
        return "ProductWithPayload{" +
                "productName='" + productName + '\'' +
                ", payload=" + payload +
                '}';
    }
}
