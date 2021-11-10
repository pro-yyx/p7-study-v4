package com.yyx.dubbo.framework;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @author yinyuxin
 * @description 负载均衡
 * @date 2021/9/15 18:34
 */
public class LoadBalance {

    public static URL random(List<URL> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return null;
        }
        Random random=new Random();
        int i = random.nextInt(urls.size());
        return urls.get(i);
    }

}
