package simpleaop;

import java.lang.reflect.Method;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: BeforeAdvice
 * Author:   copywang
 * Date:     2019/3/14 9:23
 * Description: 实现了 Advice 接口，是一个前置通知
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class BeforeAdvice implements Advice {
  private Object bean;
  private MethodInvocation methodInvocation;

  public BeforeAdvice(Object bean, MethodInvocation methodInvocation) {
    this.bean = bean;
    this.methodInvocation = methodInvocation;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // 在目标方法执行前调用通知
    methodInvocation.invoke();
    return method.invoke(bean, args);
  }
}
