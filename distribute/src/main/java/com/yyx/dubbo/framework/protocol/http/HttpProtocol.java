package com.yyx.dubbo.framework.protocol.http;

import com.yyx.dubbo.framework.Invocation;
import com.yyx.dubbo.framework.Protocol;
import com.yyx.dubbo.framework.URL;
import com.yyx.dubbo.framework.util.HttpClientUtil;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 16:25
 */
public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) {
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostName(),url.getPort());
    }

    @Override
    public String send(URL url, Invocation invocation) {
        return new HttpClientUtil().send(url.getHostName(), url.getPort(), invocation);
    }
}
