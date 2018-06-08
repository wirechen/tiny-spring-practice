package com.wirechen.ioc.io;

import com.wirechen.ioc.bean.BeanDefinition;

import java.net.URL;
import java.util.Map;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:21 2018/6/8
 * @Description:
 */
public class UrlResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }

}
