package com.wirechen.ioc.bean;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:57 2018/6/7
 * @Description: 用于保存bean对象以及其他额外的信息
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
