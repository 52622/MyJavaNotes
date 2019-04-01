/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: SingletonTest02
 * Author:   copywang
 * Date:     2018/11/13 17:05
 * Description: 饿汉式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SingletonTest02 {
  //直接实例化，不存在线程不安全问题
  private static SingletonTest02 singleton = new SingletonTest02();

  private SingletonTest02() {};

  public static SingletonTest02 getSingleton() {
    return singleton;
  }
}
