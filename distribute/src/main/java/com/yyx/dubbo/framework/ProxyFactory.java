package com.yyx.dubbo.framework;

import com.yyx.dubbo.framework.protocol.http.HttpProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yinyuxin
 * @description jdk的动态代理
 * @date 2021/9/15 18:05
 */
public class ProxyFactory {

    public static Object getProxy(Class interfaceClass){
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //缓存逻辑：在注册中心和consumer上都会有provider服务配置的缓存
                //这里只是模拟，具体dubbo中的实现看源码
                String mock = System.getProperty("mock");
                if (mock != null && mock.startsWith("return:")) {
                    String result = mock.replace("return:", "");
                    return result;
                }
                try {
                    Invocation invocation = new Invocation();
                    invocation.setIntefaceName(interfaceClass.getName()+"1.0.1");
                    invocation.setMethodName(method.getName());
                    invocation.setParamTypes(method.getParameterTypes());
                    invocation.setParams(args);
                   /* List<URL> urlList = RemoteRegister.get("helloService");
                    URL url = LoadBalance.random(urlList);*/
                    URL url = new URL();
                    url.setHostName("localhost");
                    url.setPort(8080);
                    Protocol protocol = ProtocolFactory.getProtocol();
                    return protocol.send(url, invocation);
                } catch (Exception e) {
                    //容错的原理：这里可以直接执行其他service方法
                    return "不好意思，执行出错了。";
                }
            }
        });
    }
}
