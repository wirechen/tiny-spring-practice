package com.wirechen.ioc;

import org.junit.Assert;

/**
 * @Author: WireChen
 * @Date: Created in 下午4:41 2018/6/8
 * @Description:
 */
public class OutputService {

    private HelloWorldService helloWorldService;

    public HelloWorldService getHelloWorldService() {
        return helloWorldService;
    }

    public void setHelloWorldService(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }
}
