package com.wirechen.ioc.component;

import com.wirechen.ioc.beans.annotation.Autowired;
import com.wirechen.ioc.beans.annotation.Component;

/**
 * @Author: WireChen
 * @Date: Created in 下午3:47 2018/6/14
 * @Description:
 */
@Component
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    public String doController() {
        String result = helloWorldService.doService();
        System.out.println(result);
        return result;
    }
}
