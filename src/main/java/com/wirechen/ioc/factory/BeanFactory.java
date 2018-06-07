package com.wirechen.ioc.factory;

import com.wirechen.ioc.bean.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:57 2018/6/7
 * @Description: 注册bean，获取bean
 */
public interface BeanFactory {

    // 获取bean
    Object getBean(String name);

    /**
     * 注册bean
     * @param name
     * @param beanDefinition
     */
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

}
