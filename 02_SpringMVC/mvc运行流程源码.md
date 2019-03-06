一个正常的RequestMapping匹配之后的运行流程：

1. HandlerMapping 获取HandlerExecutionChain，一个调用链，包括handler和interceptors

![1538917680145](E:\03_笔记\02_SpringMVC\assets\1538917680145.png)

![1538917699507](E:\03_笔记\02_SpringMVC\assets\1538917699507.png)

![1538918571601](E:\03_笔记\02_SpringMVC\assets\1538918571601.png)

2. 获取HandlerAdapter

![1538917815780](E:\03_笔记\02_SpringMVC\assets\1538917815780.png)

3. 执行拦截器的preHandle方法

![1538917853055](E:\03_笔记\02_SpringMVC\assets\1538917853055.png)

4. 调用目标Handler的目标方法得到ModelAndView对象

![1538917902279](E:\03_笔记\02_SpringMVC\assets\1538917902279.png)

5. 拦截器postHandle()方法

![1538917932777](E:\03_笔记\02_SpringMVC\assets\1538917932777.png)

6. 如果有抛出异常，由HandlerExceptionResolver组件处理异常，得到新的ModelAndView对象

![1538917984082](E:\03_笔记\02_SpringMVC\assets\1538917984082.png)

7. 由ViewResolver组件根据ModelAndView对象得到实际的View对象

![1538918060319](E:\03_笔记\02_SpringMVC\assets\1538918060319.png)

得到view

![1538918097932](E:\03_笔记\02_SpringMVC\assets\1538918097932.png)

渲染视图，视图和数据绑定

![1538918152357](E:\03_笔记\02_SpringMVC\assets\1538918152357.png)

8. 拦截器afterCompletion()方法

![1538918213435](E:\03_笔记\02_SpringMVC\assets\1538918213435.png)





