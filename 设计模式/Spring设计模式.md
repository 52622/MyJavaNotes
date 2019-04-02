## 静态工厂-非23之一

![img](assets/640) 

创建一个名为itxxzBean的com.itxxz.HelloItxxz类



## 工厂方法模式

应用程序自己的工厂对象交给Spring管理,那么Spring管理的就不是普通的bean,而是工厂Bean 

![img](assets/640-1554187716897) 

![img](assets/640-1554187725249) 



## 单例模式

Spring下默认的bean均为singleton，可以通过singleton=“true|false” 或者 scope="?"来指定 



## 适配器模式

Spring的Aop 

Advice（通知）来增强被代理类的功能 



1、JDK动态代理。2、CGLib字节码生成技术代理。 

![img](assets/640-1554187819130) 



## 装饰器

spring中用到的包装器模式在类名上有两种表现：一种是类名中含有Wrapper，另一种是类名中含有Decorator。基本上都是动态地给一个对象添加一些额外的职责。 



## 代理模式

Proxy是控制，更像是一种对功能的限制，而Decorator是增加职责。spring的Proxy模式在aop中有体现，比如JdkDynamicAopProxy和Cglib2AopProxy 



## 观察者模式 

spring中Observer模式常用的地方是listener的实现。如ApplicationListener 



## 策略模式

spring中在实例化对象的时候用到Strategy模式在SimpleInstantiationStrategy中有如下代码说明了策略模式的使用情况 

![img](assets/640-1554188205106) 

## 模板方法模式 

JdbcTemplate中的execute方法 

![img](assets/640-1554188251482) 

![img](assets/640-1554188256152) 











https://mp.weixin.qq.com/s?__biz=MzI1NTI3MzEwMg==&mid=2247484958&idx=1&sn=288b5d6c7de3c69cf9800ad4748b91a9&chksm=ea3937ffdd4ebee935f9556acdf66e1e825ba09e6512eb6ddaef4bdc589130e16f1990f3c9ec&mpshare=1&scene=1&srcid=&key=6c85e34880e486d5fc634be5c78c020f11638c4d50e753229bc1d6dff3ac9903edc745f5e860f2f187e197f4699b8d16f0a7ccfa097c61cac33d52ea23abd17a16d203f84b6b1b0dffbd6718c993e050&ascene=1&uin=MjgwOTc5Nzc1&devicetype=Windows+10&version=62060739&lang=zh_CN&pass_ticket=27ErwoW57S0gWfAg%2Bsvfbpc7p%2B2pwR4Zo%2FIHliEanQGfLD9qNaXbl3hlP4CT1iA4