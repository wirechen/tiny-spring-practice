package com.wirechen.ioc;

import com.wirechen.ioc.bean.BeanDefinition;
import com.wirechen.ioc.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:41 2018/6/8
 * @Description: 定义BeanDefinitionReader模板：将读取到的BeanDefinition装入registryMap中
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private ResourceLoader resourceLoader;

    private Map<String, BeanDefinition> registryMap;

    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.registryMap = new HashMap<String, BeanDefinition>();
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistryMap() {
        return registryMap;
    }
    
}
