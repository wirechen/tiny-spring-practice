package com.wirechen.ioc.beans.io;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:20 2018/6/8
 * @Description: 统一资源加载接口：
 */
public interface ResourceLoader {

    Resource getResource(String location);

}
