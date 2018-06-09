package com.wirechen.ioc.beans;

import java.io.IOException;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:34 2018/6/8
 * @Description: 定义统一BeanDefinition读取接口：实现该接口可按自定义类型读取解析BeanDefinition
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
