https://github.com/Snailclimb/JavaGuide/blob/master/docs/system-design/framework/SpringMVC%20%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86%E8%AF%A6%E8%A7%A3.md



一个正常的RequestMapping匹配之后的运行流程：

1. HandlerMapping 获取HandlerExecutionChain，一个调用链，包括handler和interceptors

![1538917680145](assets\1538917680145.png)

![1538917699507](assets\1538917699507.png)

![1538918571601](assets\1538918571601.png)

2. 获取HandlerAdapter

![1538917815780](assets\1538917815780.png)



![1538917853055](assets\1538917853055.png)

4. 调用目标Handler

![1538917902279](assets\1538917902279.png)

5. 拦截器postHandle()方法

![15389179327](assets\1538917932777.png)

6. 如果有抛出异常，由HandlerExceptionResolver组件处理异常，得到新的ModelAndView

   ![1538917984082](assets\1538917984082.png)

7. 由ViewResolver组件根据ModelAndView对象得到实际的View对象

![1538918060319](assets\1538918060319.png)

得到view

![1538918097932](assets\1538918097932.png)

渲染视图，视图和数据绑定

![15389181](assets\1538918152357.png)

8. 拦截器afterCompletion()方法

![1538918213](assets\1538918213435.png)





