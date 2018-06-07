package com.wirechen.ioc;


import com.wirechen.ioc.bean.BeanDefinition;
import com.wirechen.ioc.factory.AutowireCapableBeanFactory;
import com.wirechen.ioc.factory.BeanFactory;
import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {

        // 1、初始化bean工厂
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2、定义bean（等比读取xml文件）
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName("com.wirechen.ioc.HelloWorldService");

        // 3、向工厂注入bean
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 4、从工厂获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();

    }

}
