package com.yyx.dubbo.demo.provider.service;

import com.yyx.dubbo.demo.DemoService;
import com.yyx.dubbo.demo.DemoServiceListener;
import org.apache.dubbo.config.annotation.Argument;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:22
 */
//demoService的sayHello方法的index=2的参数是回调对象，comsumer可以调用addListner方法来添加回调对象，provider一旦执行回调对象的方法就会通知给comsumer
@Service(version = "callBack",methods = {@Method(name = "sayHello",arguments ={@Argument(index = 2,callback = true)})},callbacks = 3)
public class CallBackDemoService implements DemoService {

    private final Map<String, DemoServiceListener> listeners = new ConcurrentHashMap<>();

    public CallBackDemoService() {
        Thread t = new Thread(() -> {
            while (true) {
                for (Map.Entry<String, DemoServiceListener> entry : listeners.entrySet()) {
                    entry.getValue().changed(getChanged(entry.getKey()
                    ));
                }
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private String getChanged(String msa) {
        return "Changed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public String sayHello(String name) {
        return "callback:"+name;
    }

    @Override
    public String sayHello(String name, String key, DemoServiceListener listener) {
        System.out.println("执行了回调服务："+name);
        //listener 是由dubbo生成的代理对象
        listener.changed(name+key);
        listeners.put(key, listener);
        return "callback:"+name;
    }
}
