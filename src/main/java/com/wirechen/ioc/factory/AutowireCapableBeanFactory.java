package com.wirechen.ioc.factory;

import com.wirechen.ioc.bean.BeanDefinition;

/**
 * @Author: WireChen
 * @Date: Created in 下午6:10 2018/6/7
 * @Description: 可自动装配的工厂实现类
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 实现创建bean的方法
     * @param beanDefinition
     * @return
     */
    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) {
        try {
            Object bean = beanDefinition.getBeanClass().newInstance();
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
