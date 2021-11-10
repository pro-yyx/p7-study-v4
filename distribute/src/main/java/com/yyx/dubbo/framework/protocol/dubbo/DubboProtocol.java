package com.yyx.dubbo.framework.protocol.dubbo;

import com.yyx.dubbo.framework.Invocation;
import com.yyx.dubbo.framework.Protocol;
import com.yyx.dubbo.framework.URL;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 16:27
 */
public class DubboProtocol implements Protocol {
    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostName(),url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new NettyClient<String>().send(url.getHostName(), url.getPort(), invocation);
    }
}
