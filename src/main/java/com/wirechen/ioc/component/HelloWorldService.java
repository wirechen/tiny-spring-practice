package com.wirechen.ioc.component;

import com.wirechen.ioc.beans.annotation.Autowired;
import com.wirechen.ioc.beans.annotation.Component;

/**
 * @Author: WireChen
 * @Date: Created in 下午9:28 2018/6/13
 * @Description:
 */
@Component
public class HelloWorldService {

    @Autowired
    private HelloWorldRepository helloWorldRepository;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String doService() {
        return helloWorldRepository.queryTest();
    }
}
