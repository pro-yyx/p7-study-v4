package com.yyx.dubbo.framework;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 16:23
 */
public interface Protocol {

    void start(URL url);

    String send(URL url,Invocation invocation);
}
