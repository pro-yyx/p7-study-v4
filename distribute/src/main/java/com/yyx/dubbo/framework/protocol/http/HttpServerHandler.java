package com.yyx.dubbo.framework.protocol.http;

import com.alibaba.fastjson.JSONObject;
import com.yyx.dubbo.framework.Invocation;
import com.yyx.dubbo.framework.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/14 17:34
 */
public class HttpServerHandler {

    public void handler(HttpServletRequest request, HttpServletResponse response) {
        //处理tomcat接收到的请求逻辑
        //反序列化请求数据
        try {
            Invocation invocation = JSONObject.parseObject(request.getInputStream(), Invocation.class);
            String intefaceName = invocation.getIntefaceName();
            Class intefaceClass = LocalRegister.get(intefaceName);
            Method method = intefaceClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            String result = (String) method.invoke(intefaceClass.newInstance(), invocation.getParams());
            IOUtils.write(result,response.getOutputStream());
        } catch (IOException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
