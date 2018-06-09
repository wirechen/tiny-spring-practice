package com.wirechen.ioc;


import com.wirechen.ioc.beans.factory.AbstractBeanFactory;
import com.wirechen.ioc.beans.factory.AutowireCapableBeanFactory;
import com.wirechen.ioc.beans.factory.BeanFactory;
import com.wirechen.ioc.beans.io.UrlResourceLoader;
import com.wirechen.ioc.beans.xml.XmlBeanDefinitionReader;
import org.junit.Test;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {

        // 1、初始化bean工厂
        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();

        // 2、读取并解析xml文件
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
        beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

        // 3、注入bean
        beanDefinitionReader.getRegistryMap().forEach((name, beanDefinition) -> {
            beanFactory.registerBeanDefinition(name, beanDefinition);
        });

        // 4、初始化bean（不使用lazy-init的方式）
        beanFactory.preInstantiateSingletons();

        // 4、获取bean
        OutputService outputService = (OutputService) beanFactory.getBean("outputService");
        HelloWorldService helloWorldService = outputService.getHelloWorldService();
        helloWorldService.helloWorld();

    }

}
