package simpleaop;

import org.junit.Test;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SimpleAOPTest
 * Author:   copywang
 * Date:     2019/3/14 9:23
 * Description: SimpleAOP 从测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SimpleAOPTest {
  @Test
  public void getProxy() throws Exception {
    // 1. 创建一个 MethodInvocation 实现类
    MethodInvocation logTask = () -> System.out.println("log task start");
    HelloServiceImpl helloServiceImpl = new HelloServiceImpl();

    // 2. 创建一个 Advice
    Advice beforeAdvice = new BeforeAdvice(helloServiceImpl, logTask);

    // 3. 为目标对象生成代理
    HelloService helloServiceImplProxy = (HelloService) SimpleAOP.getProxy(helloServiceImpl,beforeAdvice);

    helloServiceImplProxy.sayHelloWorld();
  }
}
