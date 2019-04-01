/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: SingletonTest04
 * Author:   copywang
 * Date:     2018/11/13 17:33
 * Description: 静态内部类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SingletonTest04 {
  //使用类加载机制实现线程安全的单例
  private SingletonTest04() {}

  // 静态内部类
  // 调用getInstance()的时候，Singleton才会被加载，通过JVM保障INSTANCE只被实例化一次
  private static class Singleton {
    private static final SingletonTest04 INSTANCE = new SingletonTest04();
  }

  public static SingletonTest04 getInstance() {
    return Singleton.INSTANCE;
  }
}
