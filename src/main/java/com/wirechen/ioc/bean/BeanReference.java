package com.wirechen.ioc.bean;

/**
 * @Author: WireChen
 * @Date: Created in 下午4:16 2018/6/8
 * @Description: Bean依赖Bean
 */
public class BeanReference {

    private String name;

    private Object value;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
