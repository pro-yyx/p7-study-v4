package com.yyx.dubbo.demo.provider;

import org.apache.dubbo.config.spring.ServiceBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/18 17:24
 */
@Component
public class UpdateBeanPostProcessors implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.contains("ServiceBean")) {
            //在这里设置bean的id，否则一个service 有多个version或者group，都只能注入第一个service
            ServiceBean serviceBean = (ServiceBean) bean;
            serviceBean.setId(beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
