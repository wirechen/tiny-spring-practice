package com.wirechen.ioc.beans.io;


import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class UrlResourceLoaderTest {

    @Test
    public void testUrlResourceLoader() throws Exception {
        ResourceLoader resourceLoader = new UrlResourceLoader();
        InputStream inputStream = resourceLoader.getResource("tinyioc.xml").getInputStream();
        Assert.assertNotNull(inputStream);
    }

}
