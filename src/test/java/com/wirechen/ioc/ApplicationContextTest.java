package com.wirechen.ioc;

import com.wirechen.ioc.context.ApplicationContext;
import com.wirechen.ioc.context.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @Author: WireChen
 * @Date: Created in 下午4:54 2018/6/9
 * @Description:
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }
}
