package simpleaop;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: HelloServiceImpl
 * Author:   copywang
 * Date:     2019/3/14 9:24
 * Description: 目标对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class HelloServiceImpl implements HelloService {
  @Override
  public void sayHelloWorld() {
    System.out.println("hello world!");
  }
}
