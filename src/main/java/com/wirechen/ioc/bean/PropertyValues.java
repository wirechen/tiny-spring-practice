package com.wirechen.ioc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WireChen
 * @Date: Created in 下午9:13 2018/6/7
 * @Description: 用于存放PropertyValue对象集合
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();

    public PropertyValues() {
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }
}
