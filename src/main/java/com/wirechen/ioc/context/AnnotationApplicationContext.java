package com.wirechen.ioc.context;

import com.wirechen.ioc.beans.annotation.AnnotationBeanDefinitionReader;
import com.wirechen.ioc.beans.factory.AbstractBeanFactory;
import com.wirechen.ioc.beans.factory.AutowireCapableBeanFactory;
import com.wirechen.ioc.beans.io.UrlResourceLoader;
import com.wirechen.ioc.beans.xml.XmlBeanDefinitionReader;

/**
 * @Author: WireChen
 * @Date: Created in 下午10:51 2018/6/13
 * @Description:
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    private String configLocation;

    public AnnotationApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public AnnotationApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    private void refresh() throws Exception {

        AnnotationBeanDefinitionReader beanDefinitionReader = new AnnotationBeanDefinitionReader(new UrlResourceLoader());
        beanDefinitionReader.loadBeanDefinitions(configLocation);

        beanDefinitionReader.getRegistryMap().forEach((name, beanDefinition) -> {
            beanFactory.registerBeanDefinition(name, beanDefinition);
        });
    }
}
