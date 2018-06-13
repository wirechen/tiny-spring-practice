# 一起来学tiny-spring
## 目录  

- [背景](#背景)
- [Spring之IOC容器](#spring之ioc容器)
    - [step1-最基本的容器](#step1-最基本的容器)
    - [step2-bean的注册和获取由工厂负责](#step2-bean的注册和获取由工厂负责)
    - [step3-为bean注入属性](#step3-为bean注入属性)
    - [step4-用读取xml的方式来注册bean](#step4-用读取xml的方式来注册bean)
    - [step5-为bean注入bean属性](#step5-为bean注入bean属性)
    - [step6-使用lazy-init懒加载解决循环依赖问题](#step6-使用lazy-init懒加载解决循环依赖问题)
    - [step7-使用单例预加载](#step7-使用单例预加载)
    - [step8-ApplicationContext登场](#step8-applicationcontext登场)
    - step9-用Annotation方式来实现运行时注入bean
- 【Spring之AOP功能】


### 背景  
最近想深入学习一下Spring的两大核心功能：IOC、AOP。想看源码但是Spring的源码层次复杂，封装繁琐，简单的逻辑写的非常“啰嗦”，阅读起来很费劲。然后发现了多年前的一个精简版的Spring学习项目，叫[tiny-spring](https://github.com/code4craft/tiny-spring)，作者对spring核心的IOC和AOP进行了临摹实现，也很细心的对实现步骤进行了拆分。我看完了tiny-spring收获许多，自己也参考该项目进行了模仿与实践，从我学习的角度，拆分步骤更加细粒，也想在此基础上后期增加一些新的功能。

### Spring之IOC容器  
#### **step1-最基本的容器**  
#### `git checkout Spring-IOC-1`  
就两个类：
- ***BeanDefinition***：用于保存bean对象以及其他额外的信息。
- ***BeanFactory***：维护一个线程安全的ConcurrentHashMap集合，用于存取bean。  
    
测试代码：
```javascript
@Test
public void test() throws Exception {
    // 1、创建bean工厂
    BeanFactory beanFactory = new BeanFactory();

    // 2、注册bean
    BeanDefinition beanDefinition = new BeanDefinition(new HelloWorldService());
    beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

    // 3、获取bean
    HelloWorldService helloWorldService = (HelloWorldService)beanFactory.getBean("helloWorldService");
    helloWorldService.helloWorld();

}
```

#### **step2-bean的注册和获取由工厂负责**  
#### `git checkout Spring-IOC-2`  
step1中bean是由我们自己创建的，用模板设计模式优化BeanFactory，这里把bean创建交给工厂，为了保证扩展性，我们使用Extract Interface的方法，将BeanFactory替换成接口，而使用AbstractBeanFactory和AutowireCapableBeanFactory作为其实现，这里用到了模板设计模式。
- ***AbstractBeanFactory***：定义好获取bean和注册bean的方法，将具体实现doCreateBean的方法交给子类。
- ***AutowireCapableBeanFactory***：继承模板bean工厂，实现doCreateBean方法。  
    
测试代码：
```javascript
@Test
public void test() throws Exception {

    // 1、初始化bean工厂
    BeanFactory beanFactory = new AutowireCapableBeanFactory();

    // 2、定义bean（等比读取xml文件）
    BeanDefinition beanDefinition = new BeanDefinition();
    beanDefinition.setBeanClassName("com.wirechen.ioc.HelloWorldService");

    // 3、向工厂注册bean
    beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

    // 4、从工厂获取bean
    HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
    helloWorldService.helloWorld();

}
```

#### **step3-为bean注入属性**  
#### `git checkout Spring-IOC-3`  
目前我们的bean还是一个没有任何属性的，这一步将对bean注入属性。Spring本身使用了setter来进行注入，这里为了代码简洁，我们使用Field的形式来注入，创建一个`PropertyValue`类，一个bean可以有多个属性，那么再创建一个`PropertyValues`类保存PropertyValue。
- ***PropertyValue***：用key-value的形式记录bean的属性。
- ***PropertyValues***：维护一个List集合用于存取PropertyValue。
- ***AutowireCapableBeanFactory***：利用反射给`doCreateBean`方法增加赋值bean对象的属性。  
    
测试方法：
```javascript
@Test
public void test() throws Exception {

    // 1、初始化bean工厂
    BeanFactory beanFactory = new AutowireCapableBeanFactory();

    // 2、定义bean（等比读取xml文件）
    BeanDefinition beanDefinition = new BeanDefinition();
    beanDefinition.setBeanClassName("com.wirechen.ioc.HelloWorldService");
    PropertyValues propertyValues = new PropertyValues();
    propertyValues.addPropertyValue(new PropertyValue("text", "Hello World"));
    beanDefinition.setPropertyValues(propertyValues);

    // 3、注册bean
    beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);

    // 4、获取bean
    HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
    helloWorldService.helloWorld();

}
```

#### **step4-用读取xml的方式来注册bean**  
#### `git checkout Spring-IOC-4`  
这一步我们要走的就是替换step3中的第二步，把定义bean放在xml中，然后用读取xml的方式来将bean注册到工厂中。
增加一个io包，把资源获取和加载类放进去。
- ***Resource接口***：定义资源统一获取接口，实现该接口的类都要获取input流加载到内存
- ***UrlResource***：实现Resource接口，通过URL的方法来获取input流
- ***ResourceLoader接口***：统一资源加载接口，实现该接口需自定义获取资源的方法。
- ***UrlResourceLoader***：实现ResourceLoader接口，通过获取URL再加载资源。  
资源加载到内存做好了，现在我们需要去读取并解析然后封装到BeanDefinition中。
- ***BeanDefinitionReader***：定义统一BeanDefinition读取接口：实现该接口可按自定义类型读取解析BeanDefinition。
- ***AbstractBeanDefinitionReader***：定义BeanDefinitionReader模板：维护一个resourceLoader和registryMap，
- ***XmlBeanDefinitionReader***：继承AbstractBeanDefinitionReader完成具体的解析xml的工作，解析后封装到BeanDefinition并将BeanDefinition装入registryMap中。  
    
测试代码：
```javascript
@Test
public void test() throws Exception {

    // 1、初始化bean工厂
    BeanFactory beanFactory = new AutowireCapableBeanFactory();

    // 2、读取并解析xml文件
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
    beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

    // 3、注册bean
    beanDefinitionReader.getRegistryMap().forEach((name, beanDefinition) -> {
        beanFactory.registerBeanDefinition(name, beanDefinition);
    });

    // 4、获取bean
    HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
    helloWorldService.helloWorld();
}
```

#### **step5-为bean注入bean属性**</span>  
#### `git checkout Spring-IOC-5`  
之前我们为bean注入的属性都是普通类型的（String），现在为bean注入其他bean（处理bean与bean之间的依赖），定义一个BeanReference，来表示这个属性是对另一个bean的引用。
- ***BeanReference***：也是用key-value的形式统一表示引用的对象名称和对象实例。
- ***XmlBeanDefinitionReader***：修改解析xml读取方法增加判断属性是引用其他bean的逻辑。
- ***AutowireCapableBeanFactory***：修改bean属性赋值增加判断是引用的逻辑。
```javascript
    String name = propertyEle.getAttribute("name");
    String value = propertyEle.getAttribute("value");
    // 注意看xml的结构
    if (value != null && value.length() > 0) {
        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
    } else {
        String ref = propertyEle.getAttribute("ref");
        if (ref == null || ref.length() == 0) {
            throw new IllegalArgumentException("Configuration problem: <property> element for property '" + name + "' must specify a ref or value");
        }
        BeanReference beanReference = new BeanReference(ref);
        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
    }
```  
```javascript
    Object value = propertyValue.getValue();
    if (value instanceof BeanReference) {
        BeanReference beanReference = (BeanReference) value;
        value = getBean(beanReference.getName());
    }
    field.set(bean, value);
```  

测试代码：
```javascript
@Test
public void test() throws Exception {

    // 1、初始化bean工厂
    BeanFactory beanFactory = new AutowireCapableBeanFactory();

    // 2、读取并解析xml文件
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
    beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

    // 3、注册bean
    beanDefinitionReader.getRegistryMap().forEach((name, beanDefinition) -> {
        beanFactory.registerBeanDefinition(name, beanDefinition);
    });

    // 4、获取bean
    OutputService outputService = (OutputService) beanFactory.getBean("outputService");
    HelloWorldService helloWorldService = outputService.getHelloWorldService();
    helloWorldService.helloWorld();

}
```  

#### **step6-使用lazy-init懒加载解决循环依赖问题**</span>  
#### `git checkout Spring-IOC-6`  
其实在step5中是有很多问题的，比如注册顺序，如果被依赖的bean后注册就使依赖的bean找到被依赖的bean。还比如两个bean相互依赖注入到各自的属性中的时候就会造成循环依赖一直不会被创建。为了解决这两个问题这一步我们将`doCreateBean`放在`getBean`的中。这样在注入bean的时候，如果该属性对应的bean找不到，那么就先创建！因为总是先创建后注入，所以不会存在两个循环依赖的bean创建死锁的问题。
- ***AbstractBeanFactory***：把创建bean的方法从注册bean中移到获取bean中去。
```javascript
@Override
public Object getBean(String name) {
    BeanDefinition beanDefinition = beanDefinitionMap.get(name);
    if (beanDefinition == null) {
        throw new IllegalArgumentException("No bean named " + name + " is defined");
    }
    Object bean = beanDefinition.getBean();
    if (bean == null) {
        bean = doCreateBean(beanDefinition);
    }
    return bean;
}
```  
#### **step7-使用单例预加载**</span> 
#### `git checkout Spring-IOC-7`  
step6中将doCreateBean放在getBean中瞬间解决了注册顺序和循环依赖的问题，解决思路是用懒加载的方式在获取bean的时候才创建bean。我们知道Spring有单例和多例模式，我们在bean工厂中使用beanDefinitionMap就是实现的单例模式，单例模式在Spring中默认是预加载的方式，如果要实现预加载的话解决思路是在介于工厂注册bean和获取bea之间增加一个加载bean的方法。
- ***AbstractBeanFactory***：预加载的方法只需要遍历beanDefinitionMap然后调用getBean方法就能在真正使用bean之前全部加载创建所有bean。
```javascript
public void preInstantiateSingletons() {
    beanDefinitionMap.forEach((name, beanDefinition) -> {
        getBean(name);
    });
}
```    

测试代码：
```javascript
@Test
public void test() throws Exception {

    // 1、初始化bean工厂
    BeanFactory beanFactory = new AutowireCapableBeanFactory();

    // 2、读取并解析xml文件
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(new UrlResourceLoader());
    beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

    // 3、注册bean
    beanDefinitionReader.getRegistryMap().forEach((name, beanDefinition) -> {
        beanFactory.registerBeanDefinition(name, beanDefinition);
    });

    // 4、初始化bean（使用预加载）
    beanFactory.preInstantiateSingletons();

    // 4、获取bean
    OutputService outputService = (OutputService) beanFactory.getBean("outputService");
    HelloWorldService helloWorldService = outputService.getHelloWorldService();
    helloWorldService.helloWorld();

}
``` 

#### **step8-ApplicationContext登场**</span>  
#### `git checkout Spring-IOC-8`  
测试代码中我们可以将`初始化bean工厂`，`读取解析xml文件`，`注册bean`三个预准备工作全都放在我们熟悉的ApplicationContext中去完成。
- ***ApplicationContext接口***：继承BeanFactory接口，使其拥有获取bean功能。
- ***AbstractApplicationContext***：实现ApplicationContext接口，自身维护一个AbstractBeanFactory属性，对外提供getBean的方法。
- ***ClassPathXmlApplicationContext***：继承AbstractApplicationContext，主要功能是通过构造方法传入xml文件路径完成资源加载、读取、解析、注册装配等一套工作。  
    
测试代码：
```javascript
@Test
public void testApplicationContext() throws Exception{
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
    HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
    helloWorldService.helloWorld();
}
```
