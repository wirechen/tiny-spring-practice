package com.wirechen.ioc.component;

import com.wirechen.ioc.beans.annotation.Component;

/**
 * @Author: WireChen
 * @Date: Created in 下午3:04 2018/6/14
 * @Description:
 */
@Component
public class HelloWorldRepository {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String queryTest() {
        setText("Hello World!");
        return text;
    }
}
