package com.wirechen.ioc.factory;


import com.wirechen.ioc.bean.BeanDefinition;
import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {
        // 1、创建bean工厂
        BeanFactory beanFactory = new BeanFactory();

        // 2、注入bean
        BeanDefinition beanDefinition = new BeanDefinition(new HelloWorldService());
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 3、获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();

    }

}
