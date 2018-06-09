package com.wirechen.ioc.beans.io;

import java.net.URL;

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
