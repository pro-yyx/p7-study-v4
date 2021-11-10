package com.yyx.dubbo.provider;

import com.yyx.dubbo.framework.*;
import com.yyx.dubbo.framework.protocol.http.HttpProtocol;
import com.yyx.dubbo.framework.protocol.http.HttpServer;
import com.yyx.dubbo.framework.register.LocalRegister;
import com.yyx.dubbo.framework.register.RemoteRegister;
import com.yyx.dubbo.provider.api.HelloService;
import com.yyx.dubbo.provider.impl.HelloServiceImpl;

import java.io.IOException;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/14 17:28
 */
public class Provider {

    public static void main(String[] args) throws IOException {

        //首先将本地接口注册到本地的服务器当中，才能从tomcat接收的request中调用到指定的接口
        //使用后版本号，可以让同一个接口，有不同的实现

        // 注册服务



        URL url = new URL("localhost", 8080); //NetUtil
        RemoteRegister.register(HelloService.class.getName(), url);
        LocalRegister.register(HelloService.class.getName() + "1.0.1", HelloServiceImpl.class);
        //启动tomcat或者jetty（http协议）
     /*   HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);*/
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);
    }
}
