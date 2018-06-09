package com.wirechen.ioc.beans.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:13 2018/6/8
 * @Description: 定义资源统一获取接口：实现该接口的类都要获取input流加载到内存
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
