package simpleaop;

import java.lang.reflect.Proxy;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SimpleAOP
 * Author:   copywang
 * Date:     2019/3/14 9:23
 * Description: 生成代理类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SimpleAOP {
  public static Object getProxy(Object bean, Advice advice) {
    return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(),
        bean.getClass().getInterfaces(), advice);
  }
}
