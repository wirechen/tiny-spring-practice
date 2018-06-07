package com.wirechen.ioc.factory;

import com.wirechen.ioc.bean.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: WireChen
 * @Date: Created in 下午5:46 2018/6/7
 * @Description: 模板工厂虚拟父类
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();


    @Override
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        Object bean = doCreateBean(beanDefinition);
        beanDefinition.setBean(bean);
        beanDefinitionMap.put(name, beanDefinition);
    }

    // 模板设计模式：定义好全局模板，关键功能让子类实现
    protected abstract Object doCreateBean(BeanDefinition beanDefinition);
}
