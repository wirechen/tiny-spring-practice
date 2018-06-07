package com.wirechen.ioc;


import com.wirechen.ioc.bean.BeanDefinition;
import com.wirechen.ioc.bean.PropertyValue;
import com.wirechen.ioc.bean.PropertyValues;
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
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("text", "Hello World"));
        beanDefinition.setPropertyValues(propertyValues);

        // 3、注入bean
        beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

        // 4、获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();

    }

}
