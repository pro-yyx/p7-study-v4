package com.yyx.dubbo.framework;

import com.yyx.dubbo.framework.protocol.dubbo.DubboProtocol;
import com.yyx.dubbo.framework.protocol.http.HttpProtocol;

/**
 * @author yinyuxin
 * @description 引用工厂模式：否则在切换protocol协议的时候，只能通过不同实现类来申明协议：Protocol protocol=new HttpProtocol
 * @date 2021/9/18 16:37
 */
public class ProtocolFactory {

    public static Protocol getProtocol() {
        String name = System.getProperty("protocolName");
        if (name == null || "".equals(name)) {
            name = "http";
        }
        switch (name) {
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return new HttpProtocol();
    }
}
