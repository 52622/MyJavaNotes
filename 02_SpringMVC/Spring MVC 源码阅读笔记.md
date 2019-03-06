# 参考资料

视频教程：

尚硅谷 佟刚 SpringMVC B站就有

源码解析：

http://www.cnblogs.com/fangjian0423/p/springMVC-directory-summary.html

参考：

http://jinnianshilongnian.iteye.com/blog/1593441

官网资料

https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html

# 入门

基础包：

```shell
spring-aop

spring-beans

spring-context

spring-core

spring-expression

spring-web

spring-webmvc
```

配置文件

web.xml

```xml
<servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springConfig/dispatcher-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

DispatcherServlet

contextConfigLocation - 指定配置文件的路径，可以不配置，默认的配置文件为: /WEB-INF/<servlet-name>-servlet.xml，比如这里就是dispatcher-servlet.xml

dispatcher-servlet.xml

```xml
    <!-- 防止@ResponseBody出现的中文乱码 -->
    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
        <property name="messageConverters">
            <list>
                <bean class="org.springframework.http.converter.ByteArrayHttpMessageConverter"/>
                <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                    <property name="supportedMediaTypes">
                        <list>
                            <value>text/plain;charset=UTF-8</value>
                        </list>
                    </property>
                </bean>
                <bean class="org.springframework.http.converter.ResourceHttpMessageConverter"/>
            </list>
        </property>
    </bean>
    <!-- 防止@ResponseBody出现的中文乱码 -->

    <mvc:resources location="/static/" mapping="/static/**"/>
    <mvc:annotation-driven/>
    <context:component-scan base-package="org.format.demo.controller" />


    <!--
    <import resource="classpath:springConfig/viewConfig/jsp.xml"/>
    -->
    <import resource="classpath:springConfig/viewConfig/freemarker.xml"/>
```

包扫描 - controller

静态文件映射 - css/js

视图解析器 - jsp/freemarker

以2种静态文件映射为主，SpringBoot已不支持JSP

jsp的

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
          http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
       default-autowire="byName">

    <!-- jsp视图 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/view/"></property>
        <property name="suffix" value=".jsp"></property>
    </bean>

</beans>
```

freemarker的

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
          http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"
          default-autowire="byName">

    <bean id="freemarkerConfig" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
        <property name="templateLoaderPath" value="/WEB-INF/view/" />
        <property name="defaultEncoding" value="utf-8" />
        <property name="freemarkerSettings">
            <props>
                <prop key="template_update_delay">10</prop>
                <prop key="locale">zh_CN</prop>
                <prop key="datetime_format">yyyy-MM-dd</prop>
                <prop key="date_format">yyyy-MM-dd</prop>
                <prop key="number_format">#.##</prop>
            </props>
        </property>
    </bean>

    <!-- freemarker视图  -->
    <bean id="viewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.freemarker.FreeMarkerView"></property>
        <property name="suffix" value=".ftl" />
        <property name="contentType" value="text/html;charset=utf-8" />
        <property name="exposeRequestAttributes" value="true" />
        <property name="exposeSessionAttributes" value="true" />
        <property name="exposeSpringMacroHelpers" value="true" />
        <property name="requestContextAttribute" value="request"/>
    </bean>

</beans>
```

一个controller，会被spring容器初始化

```java
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("index");
        view.addObject("welcome", "hello");
        return view;
    }

}
```

@RequestMapping("/abc") 和 @RequestMapping("abc") 效果一样

注意是否有配置contextPath，有的话路径就要加上contextPath

这里返回的index，和视图解析器配置的/WEB-INF/view/{index}.ftl组成一个完整的静态文件路径

几个点：

1. 映射的最终路径，@RequestMapping，由类名配置的value和方法的value合起来成为一个，比如http://hostname:port/contextPath/a/b，类上标注@RequestMapping("/a")，方法标注@RequestMapping("/b")
2. 可以指定方法，@RequestMapping(method=RequestMethod.POST)，支持GET、POST、PUT、DELETE等，请求方法不匹配，响应码405，在springboot中可以直接使用@GetMapping/@PostMapping等省去书写method的步骤
3. @PathVariable，从请求路径获取参数
4. 接收参数为一个实体的时候，会自动注入到实体



## 分析

DispatcherServlet - 初始化过程，处理请求过程

继承关系

![1537973487026](E:\03_笔记\02_SpringMVC\assets\1537973487026.png)

HttpServletBean.java

```java
    public final void init() throws ServletException {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Initializing servlet '" + this.getServletName() + "'");
        }

        try {
            PropertyValues pvs = new HttpServletBean.ServletConfigPropertyValues(this.getServletConfig(), this.requiredProperties);
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            ResourceLoader resourceLoader = new ServletContextResourceLoader(this.getServletContext());
            bw.registerCustomEditor(Resource.class, new ResourceEditor(resourceLoader, this.getEnvironment()));
            this.initBeanWrapper(bw);
            bw.setPropertyValues(pvs, true);
        } catch (BeansException var4) {
            this.logger.error("Failed to set bean properties on servlet '" + this.getServletName() + "'", var4);
            throw var4;
        }

        this.initServletBean();
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Servlet '" + this.getServletName() + "' configured successfully");
        }

    }
```

静态内部类 ServletConfigPropertyValues

```java
    private static class ServletConfigPropertyValues extends MutablePropertyValues {
        public ServletConfigPropertyValues(ServletConfig config, Set<String> requiredProperties) throws ServletException {
            Set<String> missingProps = requiredProperties != null && !requiredProperties.isEmpty() ? new HashSet(requiredProperties) : null;
            Enumeration en = config.getInitParameterNames();

            while(en.hasMoreElements()) {
                String property = (String)en.nextElement();
                Object value = config.getInitParameter(property);
                this.addPropertyValue(new PropertyValue(property, value));
                if (missingProps != null) {
                    missingProps.remove(property);
                }
            }

            if (missingProps != null && missingProps.size() > 0) {
                throw new ServletException("Initialization from ServletConfig for servlet '" + config.getServletName() + "' failed; the following required properties were missing: " + StringUtils.collectionToDelimitedString(missingProps, ", "));
            }
        }
    }
```

