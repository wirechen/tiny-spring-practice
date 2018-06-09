package com.wirechen.ioc.context;

import com.wirechen.ioc.beans.BeanDefinition;
import com.wirechen.ioc.beans.factory.AbstractBeanFactory;
import com.wirechen.ioc.beans.factory.BeanFactory;

/**
 * @Author: WireChen
 * @Date: Created in 下午4:28 2018/6/9
 * @Description:
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) throws Exception {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getBean(String name) {
        return beanFactory.getBean(name);
    }

}
