package com.yyx.dubbo.framework.register;

import com.yyx.dubbo.framework.URL;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/14 17:48
 */
public class LocalRegister {

    //本地注册：将接口和方法注册到本地的对象池
    private static Map<String, Class> map = new HashMap<>();


    public static void register(String interfaceName, Class interfaceClass) {
        map.put(interfaceName, interfaceClass);
    }

    public static Class get(String interfaceName) {
        return map.get(interfaceName);
    }


}
