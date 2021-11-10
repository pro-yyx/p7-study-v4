package com.yyx.dubbo.demo.provider.service;

import com.yyx.dubbo.demo.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:22
 */
@Service(version = "generic",interfaceName = "com.yyx.dubbo.demo.DemoService")
public class GenericDemoService implements GenericService {

    @Override
    public Object $invoke(String s, String[] strings, Object[] objects) throws GenericException {
        return "generic:"+s;
    }
}
