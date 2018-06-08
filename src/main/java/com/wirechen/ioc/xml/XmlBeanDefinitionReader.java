package com.wirechen.ioc.xml;

import com.wirechen.ioc.AbstractBeanDefinitionReader;
import com.wirechen.ioc.bean.BeanDefinition;
import com.wirechen.ioc.bean.PropertyValue;
import com.wirechen.ioc.bean.PropertyValues;
import com.wirechen.ioc.io.Resource;
import com.wirechen.ioc.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * @Author: WireChen
 * @Date: Created in 下午1:53 2018/6/8
 * @Description: 读取xml的方式解析BeanDefinition
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    // 父类有非空构造方法子类必须调用
    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        InputStream inputStream = resource.getInputStream();
        doLoadBeanDefinitions(inputStream);
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        // 解析bean
        registerBeanDefinitions(doc);
        inputStream.close();
    }

    private void registerBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();
        parseBeanDefinitions(root);
    }

    private void parseBeanDefinitions(Element root) {
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }
    }

    private void processBeanDefinition(Element ele) {
        String name = ele.getAttribute("name");
        String className = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(className);
        processProperty(ele,beanDefinition);
        getRegistryMap().put(name, beanDefinition);  //将BeanDefinition装入registryMap
    }

    private void processProperty(Element ele,BeanDefinition beanDefinition) {
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
            }
        }
    }

}
