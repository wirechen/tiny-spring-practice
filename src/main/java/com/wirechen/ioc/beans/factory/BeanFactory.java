package com.wirechen.ioc.beans.factory;

import com.wirechen.ioc.beans.BeanDefinition;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:57 2018/6/7
 * @Description: 注册bean，获取bean
 */
public interface BeanFactory {

    // 获取bean
    Object getBean(String name);

}
