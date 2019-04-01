/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: SingletonTest01
 * Author:   copywang
 * Date:     2018/11/13 16:56
 * Description: 懒汉式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SingletonTest01 {

  // 私有静态变量
  private static SingletonTest01 singleton;

  // 私有构造函数
  private SingletonTest01() {

  }

  // 公有静态方法
  public static SingletonTest01 getSingleton() {
    // lazy模式，没有用到这个类，就不会实例化，节省资源
    // 线程不安全，多个线程同时进入if，且此时为null，就会创建多个实例
    if(singleton == null) {
      singleton = new SingletonTest01();
    }
    return singleton;
  }

}
