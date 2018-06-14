package com.wirechen.ioc.beans.annotation;

import com.sun.xml.internal.txw2.IllegalAnnotationException;
import com.wirechen.ioc.beans.AbstractBeanDefinitionReader;
import com.wirechen.ioc.beans.BeanDefinition;
import com.wirechen.ioc.beans.BeanReference;
import com.wirechen.ioc.beans.PropertyValue;
import com.wirechen.ioc.beans.io.Resource;
import com.wirechen.ioc.beans.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * @Author: WireChen
 * @Date: Created in 下午9:23 2018/6/13
 * @Description: 解析注解的方法读取beanDefinition
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader{

    private static final String BASE_PROJECT_PATH = System.getProperty("user.dir") + "/src/main/java/";

    private static String CLASS_PACKAGE_NAME;

    public AnnotationBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        InputStream inputStream = resource.getInputStream();
        CLASS_PACKAGE_NAME = loadAnnotationConfig(inputStream);
        String searchFilePath = BASE_PROJECT_PATH + CLASS_PACKAGE_NAME.replace(".", "/");
        File file = new File(searchFilePath);
        scanPackages(file);
    }

    private String loadAnnotationConfig(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        inputStream.close();
        Element root = doc.getDocumentElement();
        NodeList nl = root.getChildNodes();
        String basePackage = "";
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                basePackage = ele.getAttribute("base-package");
                break;
            }
        }
        return basePackage;
    }

    private void scanPackages(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        scanPackages(fileArray[i]);
                    }
                }
            } else {
                String className = CLASS_PACKAGE_NAME
                        + f.getPath().split(CLASS_PACKAGE_NAME.replace(".", "/"))[1].replace("/", ".").split(".java")[0];
                inspectComponent(className); //如果是文件就检查是否有@Component注解
            }
        }
    }

    private void inspectComponent(String className) {
        try {
            Class clazz = Class.forName(className);
            boolean isExist = clazz.isAnnotationPresent(Component.class);
            if (isExist) {
                Component component = (Component) clazz.getAnnotation(Component.class);
                String beanName = "";
                if ("".equals(component.value())) {
                    beanName = lowerCase(clazz.getSimpleName());
                } else { //如果Component有值则按自定义值注入
                    beanName = component.value();
                }
                BeanDefinition beanDefinition = new BeanDefinition();
                beanDefinition.setBeanClassName(clazz.getName());
                processProperty(beanDefinition, clazz);
                getRegistryMap().put(beanName, beanDefinition);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processProperty(BeanDefinition beanDefinition, Class clazz) throws ClassNotFoundException {
        for (Field field : clazz.getDeclaredFields()) {
            boolean isExist = field.isAnnotationPresent(Autowired.class);
            String fieldName = field.getName();
            String simpleName = field.getType().getSimpleName();
            Class fieldClazz = Class.forName(field.getType().getName());
            boolean isFieldExist = fieldClazz.isAnnotationPresent(Component.class);
            if (isExist) {
                if (!isFieldExist) {
                    throw new IllegalAnnotationException(simpleName + "has no @Component annotation, can not inject it..");
                } else {
                    Component component = (Component) fieldClazz.getAnnotation(Component.class);
                    String beanName = "";
                    if ("".equals(component.value())) {
                        beanName = lowerCase(fieldClazz.getSimpleName());
                    } else { //如果Component有值则按自定义值注入
                        beanName = component.value();
                    }
                    BeanReference beanReference = new BeanReference(beanName);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(field.getName(), beanReference));
                }

            } else {
                if (isFieldExist) {
                    throw new IllegalAnnotationException(simpleName + "has @Component annotation, you can not inject it with no @Autowired");
                } else {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(fieldName, ""));
                }
            }
        }
    }

    private String lowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

}
