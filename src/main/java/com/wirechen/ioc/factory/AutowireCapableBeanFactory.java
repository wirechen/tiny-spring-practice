package com.wirechen.ioc.factory;

import com.wirechen.ioc.bean.BeanDefinition;
import com.wirechen.ioc.bean.BeanReference;
import com.wirechen.ioc.bean.PropertyValue;

import java.lang.reflect.Field;
import java.util.List;

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
            Object bean = createBeanInstance(beanDefinition);
            beanDefinition.setBean(bean);
            applyPropertyValues(bean, beanDefinition);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建bean实例
     * @param beanDefinition
     * @return
     * @throws Exception
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance(); //默认构造方法new一个属性都为空的对象
    }

    /**
     * 给bean设置属性
     * @param bean
     * @param beanDefinition
     * @throws Exception
     */
    protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValues().getPropertyValueList();
        for (PropertyValue propertyValue : propertyValueList) {
            Field field = bean.getClass().getDeclaredField(propertyValue.getName());
            field.setAccessible(true);
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }
            field.set(bean, value);
        }
    }
}
