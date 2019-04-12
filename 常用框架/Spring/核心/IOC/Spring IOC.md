参考资料：雷丰阳的Spring注解驱动开发



## @Configuration和@Bean

基本配置文件和配置类

spring基本配置文件

-注入一个bean和初始属性

```xml
<bean id="person" class="com.test.Person">
	<property name="age" value="18"></property>
    <property name="name" value="hello"></property>
</bean>
```

如何取出这个bean，new一个容器对象，然后根据id或者类型取出bean

```java
new ClassPathXmlApplicationContext("beans.xml") //返回一个ApplicationContext

ApplicationContext.getBean('person'); //按照id取出bean

ApplicationContext.getBean(Person.class);//按照类型取出bean
```



配置类来代替配置文件

新建一个配置类 MainConfig

类型默认是返回类型，id是方法名

```java
@Configuration
public class MainConfig {
    @Bean
    public Person person(){
        return new Person("hello",18);
    }
}
```

怎么取出来：new一个容器对象，按照类型或者id取出bean

```java
new AnnotationConfigApplicationConext(MainConfig.class);//传入主配置类，返回一个ApplicationContext
```

可以使用ApplicationContext.getBeanNamesByType(Person.class)，拿到bean的id

@Bean的参数可以加@Bean(value = "person")，指定bean的id

## @ComponentScan

包扫描

配置文件的方式

```xml
<context:component-scan base-package="com.xxx"></context:component-scan>
```

再这个包路径下的，只要标注了

```java
@Controller
@Service
@Component
@Repository
```

以上注解的bean都会被加入到容器中



配置类的方式

@ComponentScan(value="com.xxx")

```java
@Configuration
@ComponentScan(value="com.xxx")
public class MainConfig {
    @Bean
    public Person person(){
        return new Person("hello",18);
    }
}
```

同样，标注了上面4个注解的bean也会被加入到容器中

ComponentScan可以指定排除，或者只扫描某些包

观察@ComponentScan中的excludeFilters，是一个Filter数组，而Filter是一个注解，这个注解传入2个参数，第一个是按照类型，或者是按照其他，第二个参数就是那些类型或者其他需要被排除

```java
@ComponentScan(value="com.xxx", excludeFilters = {
    @Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})//按照注解类型排除
})
```

同理可以观察includeFilters

注意，要禁用默认的过滤规则，以前在配置文件里面，user-default-filters = false

```java
@ComponentScan(value="com.xxx", includeFilters = {
    @Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})//按照注解类型只扫描哪些包
},useDefaultFilters=false)
```

ComponentScan在JDK8是@Repeatable的，可以同时配置多个

JDK8以前的是用ComponentScans，传入一个value为ComponentScan的数组

过滤器是有先后顺序的

## TypeFilter

自定义规则

不止可以用ANNOTATION来过滤，可以查看Filter注解可以支持的类型

```text
ASSIGNABLE_TYPE，按照类型过滤，传入xx.class;
ASPECTJ，ASPECTJ表达式过滤
REGEX，正则表达式过滤
CUSTOM，自定义规则
```

自定义规则要实现TypeFilter接口

实现match方法，返回一个boolean

两个参数，metadataReader当前正在扫描的类的信息，metadataReaderFactory可以获取其他任何类信息

信息包括：

注解的信息（AnnotationMetadata），类信息（ClassMetadata），类资源信息（Resource）（路径等）

## @Scope

设置组件作用域

IOC容器中，组件默认都是单实例的

可以观察@Scope注解，可以看到可以配置的值

```java
public class MainConfig2 {
	
	//默认是单实例的
	/**
	 * ConfigurableBeanFactory#SCOPE_PROTOTYPE    
	 * @see ConfigurableBeanFactory#SCOPE_SINGLETON  
	 * @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
	 * @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION	 sesssion
	 * @return\
	 * @Scope:调整作用域
	 * prototype：多实例的：ioc容器启动并不会去调用方法创建对象放在容器中。
	 * 					每次获取的时候才会调用方法创建对象；
	 * singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。
	 * 			以后每次获取就是直接从容器（map.get()）中拿，
	 * request：同一次请求创建一个实例
	 * session：同一个session创建一个实例
	 * 
	 */
	@Scope("prototype")
	@Bean("person")
	public Person person(){
		System.out.println("给容器中添加Person....");
		return new Person("张三", 25);
	}
}
```

## 懒加载 - 单实例bean

```java
public class MainConfig2 {
	
	//默认是单实例的
	/**
	 * 懒加载：
	 * 		单实例bean：默认在容器启动的时候创建对象；
	 * 		懒加载：容器启动不创建对象。第一次使用(获取)Bean创建对象，并初始化；
	 * 
	 */
	@Lazy
	@Bean("person")
	public Person person(){
		System.out.println("给容器中添加Person....");
		return new Person("张三", 25);
	}
```

## @Conditional

spring4.0后提供，按照一定的条件判断，满足条件就注册bean

在springboot底层大量使用

接收的是一个数组

```java
//类中组件统一设置。满足当前条件，这个类中配置的所有bean注册才能生效；
@Conditional({WindowsCondition.class})
@Configuration
public class MainConfig2 {
	
	/**
	 * @Conditional({Condition}) ： 按照一定的条件进行判断，满足条件给容器中注册bean
	 * 
	 * 如果系统是windows，给容器中注册("bill")
	 * 如果是linux系统，给容器中注册("linus")
	 */
	
	@Bean("bill")
	public Person person01(){
		return new Person("Bill Gates",62);
	}
	
	@Conditional(LinuxCondition.class)
	@Bean("linus")
	public Person person02(){
		return new Person("linus", 48);
	}
}
```

容器可以获取环境变量

applicationContext.getEnvironment();

判断条件要实现Condition接口

```java
package com.atguigu.condition;

import org.springframework.beans.ioc.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.ioc.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//判断是否linux系统
public class LinuxCondition implements Condition {

	/**
	 * ConditionContext：判断条件能使用的上下文（环境）
	 * AnnotatedTypeMetadata：注释信息
	 */
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// TODO是否linux系统
		//1、能获取到ioc使用的beanfactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//2、获取类加载器
		ClassLoader classLoader = context.getClassLoader();
		//3、获取当前环境信息
		Environment environment = context.getEnvironment();
		//4、获取到bean定义的注册类
		BeanDefinitionRegistry registry = context.getRegistry();
		
		String property = environment.getProperty("os.name");
		
		//可以判断容器中的bean注册情况，也可以给容器中注册bean
		boolean definition = registry.containsBeanDefinition("person");
		if(property.contains("linux")){
			return true;
		}
		
		return false;
	}

}
```





