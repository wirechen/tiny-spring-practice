package com.wirechen.ioc.factory;

import com.wirechen.ioc.bean.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:57 2018/6/7
 * @Description: 注册bean，获取bean
 */
public class BeanFactory {

    /**
     * 定义一个线程安全的beanDefinitionMap用于工厂存放一些Beandefinition对象
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    /**
     * 获取bean
     * @return
     */
    public Object getBean(String name) {
        return this.beanDefinitionMap.get(name).getBean();
    }

    /**
     * 注册bean
     * @param name
     * @param beanDefinition
     */
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
    }

}
