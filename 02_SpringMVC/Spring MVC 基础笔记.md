### 基本信息

版本4.x

通过注解，实现普通java类成为控制器，不用手动实现接口

支持REST风格的URL

基础demo

基础包：

```shell
spring-aop

spring-beans

spring-context

spring-core

spring-expression

spring-web

spring-webmvc

commons-logging
```

配置文件 

web.xml - DispatcherServlet

1. 配置servlet，参数为配置文件的路径，容器启动时servlet加入到容器中
2. 配置访问路径匹配

```xml
	<!-- 配置 DispatcherServlet -->
	<servlet>
		<servlet-name>dispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:springmvc.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>dispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
```

springmvc.xml

1. 配置包扫描
2. 配置视图解析器

```xml
<!-- 配置自定扫描的包 -->
	<context:component-scan base-package="com.atguigu.springmvc"></context:component-scan>

	<!-- 配置视图解析器: 如何把 handler 方法返回值解析为实际的物理视图 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>
```

实体类

1. 使用@Controller在类上标注，表示这个类为一个处理器
2. 使用@RequestMapping在方法上配置访问路径，方法返回字符串，通过视图解析器返回物理视图

PS：

init-param是可以不配置的，会使用默认的/WEB-INF/[servlet-name]-servlet.xml，比如上面这个就是dispatcherServlet-servlet.xml



@RequestMapping可以修饰类和方法，最终的映射路径是类上的加上方法上的路径拼接，@RequestMapping("a")和@RequestMapping("/a")是一样的效果。

HTTP：请求方法+请求URL+HTTP协议版本+请求头+请求体

@RequestMapping可以指定：value/method/params/headers，value是路径，method是请求方式GET/POST/PUT/DELETE

params是请求参数，headers是请求头，这两个支持简单的表达式，param必须包含，!param不能包含，param!=xxx包含不等于xxx的参数，{"param1=value1","param2"}包含2个参数，切第一个参数值为指定值

还有2个consumes/produces，指定接收的报文体类型，json或者form等，指定返回的数据类型，一般是json

@RequestMapping可以支持通配符，?和*和**，分别表示一个，任意，多层路径，比如

```java
@RequestMapping("/a/*/b")
```

@PathVariable()可以获取URL的占位符

```java
@RequestMapping("/a/{id}")
public String xxx(@PathVariable("id") String id)
```

REST

```shell
GET/{id} - 获取
POST - 新增
PUT/{id} - 修改
DELETE/{id} - 删除
```

过滤器，HiddenHttpMethodFilter，浏览器form表单只支持GET与POST请求，而DELETE、PUT等method并不支持 ，用于把PUT/DELETE转换成标准的http方法

PS：问题是，浏览器不支持，所以才需要转化

配置web.xml

```xml
	<!-- 
	配置 org.springframework.web.filter.HiddenHttpMethodFilter: 可以把 POST 请求转为 DELETE 或 POST 请求 
	-->
	<filter>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
	</filter>
	
	<filter-mapping>
		<filter-name>HiddenHttpMethodFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```

注意

```shell
客户端提交请求时必须使用POST方式，然后再多加一个参数 (_method = "PUT")，HiddenHttpMethodFilter 就会隐藏掉POST，在后续逻辑代码中获得的请求方式将都是 PUT 。DELETE 等其他请求方式同理。

另：_method这个参数名是 HiddenHttpMethodFilter 默认的，也可以在web.xml中配置的时候指定自己的参数名称。
```

源码覆写了doFilterInternal方法，要求客户端必须使用POST方式，新建了一个HttpServletRequest实例，其实现类是HttpMethodRequestWrapper，这里覆写了getMethod方法，也就是获取到PUT/DELETE，然后转发filterChain.doFilter(wrapper, response); 

前端JSP示例：

```html
<form action="xxx/delete/1" method="post">
    <input type="hidden" name="_method" value="DELETE"/>
    <input type="submit" value="delete"/>
</form>
```

@RequestParam

请求参数，可以指定是否必须，默认值

请求地址是

```html
http://ip:port/xxx?username=admin&password=admin
```

方法定义

```java
public String login((@RequestParam(value="username") String userName),@RequestParam(value="password", require=false,defaultValue="admin") String password)
```

@RequestHeader

请求头，同样可以指定是否必须，默认值

```java
public String login((@RequestHeader(value="Accept-Language") String lang)
```

@CookieValue

获取cookie的值，同样可以指定是否必须，默认值

```java
public String login((@CookieValue(value="JSESSIONID") String cookie)
```

自动封装实体，根据属性自动注入实体，支持继承和嵌套

```java
@RequestMapping("/user")
public String sendUser(User user) {
    return "ok";
}

class User extends Person{
    private String username;
    private String password;
    private Address address;
}

class Address {
    private String city;
}
```

Servlet API可以作为参数

```java
HttpServletRequest
HttpServletResponse
HttpSession
java.security.Principal
Locale
InputStream
OutputStream
Reader
Writer
```

175行，args就是req和res

![1538643225900](E:\03_笔记\02_SpringMVC\assets\1538643225900.png)

arg的方法

![1538643313362](E:\03_笔记\02_SpringMVC\assets\1538643313362.png)

先取出注解类型的变量

![1538643359094](E:\03_笔记\02_SpringMVC\assets\1538643359094.png)

如果没有注解类型的

![1538643412499](E:\03_笔记\02_SpringMVC\assets\1538643412499.png)

![1538643458214](E:\03_笔记\02_SpringMVC\assets\1538643458214.png)

![1538643537159](E:\03_笔记\02_SpringMVC\assets\1538643537159.png)

![1538643551505](E:\03_笔记\02_SpringMVC\assets\1538643551505.png)

这些就是标准的servlet API了

返回Model

```java
ModleAndView，方法体通过这个对象添加模型数据
Map和Model，入参为org.springframework.ui.Model/ModelMap或者java.util.Map的时候，返回值会自动添加到模型中
@SessionAttributes，模型的某个属性存入到HttpSession中，可以共享
@ModelAttribute，入参注解，对象会被放入到数据模型中
```

第一种：

```java
@RequestMapping("/login")
public ModelAndView login(){
    String viewName = "login";//视图login.jsp
    ModelAndView modelAndView = new ModelAndView(viewName);
    modelAndView.addObject("name","hello");
}
```

jsp取出

```html
name: ${requestScope.name }
```

源代码：

MVC最后都会返回一个ModelAndView(意味着String Map等都会被转成mv)

![1538644906510](E:\03_笔记\02_SpringMVC\assets\1538644906510.png)

处理结果

![1538644974530](E:\03_笔记\02_SpringMVC\assets\1538644974530.png)

处理异常和渲染视图

![1538645054816](E:\03_笔记\02_SpringMVC\assets\1538645054816.png)

![1538645117906](E:\03_笔记\02_SpringMVC\assets\1538645117906.png)

输出视图

![1538645226160](E:\03_笔记\02_SpringMVC\assets\1538645226160.png)

把模型暴露到属性中

![1538645284592](E:\03_笔记\02_SpringMVC\assets\1538645284592.png)

遍历Map，把值放入到请求域

![1538645340641](E:\03_笔记\02_SpringMVC\assets\1538645340641.png)

入参的model就是

![1538645584782](E:\03_笔记\02_SpringMVC\assets\1538645584782.png)



第二种：

```java
@RequestMapping("/login")
public String login(Map<String,Object> map) {
    map.put("names",Arrays.asList("a","b","c"));
    return "login";
}
```

jsp

```html
names: {requestScope.names}
```

实际返回值是，要么是model，要么是map

![1538645886752](E:\03_笔记\02_SpringMVC\assets\1538645886752.png)



![1538645951102](E:\03_笔记\02_SpringMVC\assets\1538645951102.png)

放入请求域的方法：

![1538646038804](E:\03_笔记\02_SpringMVC\assets\1538646038804.png)



第三种：

@SessionAttributes 注解标注在类上

```java
@SessionAttributes(types=User.class)
@SessionAttributes(value={"user1","user2"})
@SessionAttributes(type={User.class,Dept.class})
@SessionAttributes(value={"user1","user2"},types={Dept.class}) //或关系
```

jsp

```html
user: sessionScope.user
```



第四种：重点分析

@ModelAttribute

运行过程

```java
1. 执行 @ModelAttribute 注解修饰的方法: 从数据库中取出对象, 把对象放入到了 Map 中，key为: user
2. SpringMVC 从 Map 中取出 User 对象, 并把表单的请求参数赋给该 User 对象的对应属性
3. SpringMVC 把上述对象传入目标方法的参数
```

源码分析：重要

todo - P20-23

![1538663254884](E:\03_笔记\02_SpringMVC\assets\1538663254884.png)



### 视图解析流程

视图解析器：以JSP为例

```xml
<!-- 配置视图解析器: 如何把 controller 方法返回值解析为实际的物理视图 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/"></property>
		<property name="suffix" value=".jsp"></property>
	</bean>
```

返回字符串的时候，如何解析成对应的JSP？

view.class

viewResolve.class

过程分析：

无论是返回String还是View还是ModelAndView，最终都会被转换成ModelAndView

![1538704010280](E:\03_笔记\02_SpringMVC\assets\1538704010280.png)

处理方法，并渲染

![1538704098347](E:\03_笔记\02_SpringMVC\assets\1538704098347.png)



生成一个视图对象，并调用对应视图对象的渲染方法

![1538704162520](E:\03_笔记\02_SpringMVC\assets\1538704162520.png)

这里获得的View中的URL就是最终的物理视图路径

![1538704483856](E:\03_笔记\02_SpringMVC\assets\1538704483856.png)

把model的数据绑定到视图中

![1538704222220](E:\03_笔记\02_SpringMVC\assets\1538704222220.png)

默认使用InternalResourceView(如果有加入JSTL的包，会使用jstlView)

![1538704247040](E:\03_笔记\02_SpringMVC\assets\1538704247040.png)

转发请求

![1538704281847](E:\03_笔记\02_SpringMVC\assets\1538704281847.png)

完成

### 国际化

todo

### 自定义视图解析器

todo

### 重定向和转发

```java
return "redirect:/index/xxx.jsp";
return "forward:/index/xxx.jsp";
```

原理，在生成视图的时候判断是否是转发或者重定向

![1538719417307](E:\03_笔记\02_SpringMVC\assets\1538719417307.png)



### 一个简单的CRUD

问题：

springmvc的静态资源映射

字符串转Date类型

数据类型格式化

数据校验

### 数据绑定流程

数据绑定和校验

![1538789072203](E:\03_笔记\02_SpringMVC\assets\1538789072203.png)

转换器，用于从页面传入的类型和实体类型不一致的时候，进行数据类型转换

ConversionService

![1538790527633](E:\03_笔记\02_SpringMVC\assets\1538790527633.png)



自定义类型转换器，可以自定义，但是一般不用

比如字符串直接转对象

annotation-driven

































