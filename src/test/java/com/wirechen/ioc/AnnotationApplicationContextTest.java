package com.wirechen.ioc;

import com.wirechen.ioc.component.HelloWorldController;
import com.wirechen.ioc.context.AnnotationApplicationContext;
import com.wirechen.ioc.context.ApplicationContext;
import org.junit.Test;

/**
 * @Author: WireChen
 * @Date: Created in 下午4:01 2018/6/14
 * @Description:
 */
public class AnnotationApplicationContextTest {

    @Test
    public void testAnnotationApplicationContext() throws Exception{
        ApplicationContext applicationContext = new AnnotationApplicationContext("applicationContext.xml");
        HelloWorldController helloWorldController = (HelloWorldController) applicationContext.getBean("helloWorldController");
        helloWorldController.doController();
    }
}
