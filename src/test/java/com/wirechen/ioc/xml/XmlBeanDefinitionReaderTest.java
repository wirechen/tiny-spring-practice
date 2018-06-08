package com.wirechen.ioc.xml;


import com.wirechen.ioc.bean.BeanDefinition;
import com.wirechen.ioc.io.UrlResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void testXmlBeanDefinitionReader() throws Exception {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
        beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
        Map<String, BeanDefinition> registryMap = beanDefinitionReader.getRegistryMap();
        Assert.assertTrue(registryMap.size() > 0);
    }

}
