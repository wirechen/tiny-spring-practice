package com.wirechen.ioc.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:14 2018/6/8
 * @Description: Url资源获取
 */
public class UrlResource implements Resource {

    private URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

}
