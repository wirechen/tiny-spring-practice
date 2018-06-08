package com.wirechen.ioc;


import com.wirechen.ioc.factory.AutowireCapableBeanFactory;
import com.wirechen.ioc.factory.BeanFactory;
import com.wirechen.ioc.io.UrlResourceLoader;
import com.wirechen.ioc.xml.XmlBeanDefinitionReader;
import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {

        // 1、初始化bean工厂
        BeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2、读取并解析xml文件
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
        beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

        // 3、注入bean
        beanDefinitionReader.getRegistryMap().forEach((name, beanDefinition) -> {
            beanFactory.registerBeanDefinition(name, beanDefinition);
        });

        // 4、获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }

}
