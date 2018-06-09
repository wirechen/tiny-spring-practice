package com.wirechen.ioc.beans;

/**
 * @Author: WireChen
 * @Date: Created in 下午9:14 2018/6/7
 * @Description: 用于存放bean的属性名和值并注入到bean
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
