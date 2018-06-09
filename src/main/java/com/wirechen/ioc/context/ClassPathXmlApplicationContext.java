package com.wirechen.ioc.context;

import com.wirechen.ioc.beans.BeanDefinition;
import com.wirechen.ioc.beans.BeanDefinitionReader;
import com.wirechen.ioc.beans.factory.AbstractBeanFactory;
import com.wirechen.ioc.beans.factory.AutowireCapableBeanFactory;
import com.wirechen.ioc.beans.io.UrlResourceLoader;
import com.wirechen.ioc.beans.xml.XmlBeanDefinitionReader;

/**
 * @Author: WireChen
 * @Date: Created in 下午4:44 2018/6/9
 * @Description:
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext{

    private String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory abstractBeanFactory) throws Exception {
        super(abstractBeanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    /**
     * 资源加载、读取、解析、装配
     * @throws Exception
     */
    private void refresh() throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);

        xmlBeanDefinitionReader.getRegistryMap().forEach((name, beanDefinition) -> {
            beanFactory.registerBeanDefinition(name, beanDefinition);
        });
    }
}
