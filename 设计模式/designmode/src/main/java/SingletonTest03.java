/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: SingletonTest03
 * Author:   copywang
 * Date:     2018/11/13 17:15
 * Description: 双重校验锁
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SingletonTest03 {
  // 私有静态变量
//  private static SingletonTest03 singleton; //线程不安全的定义
  private static volatile SingletonTest03 singleton;

  // 私有构造函数
  private SingletonTest03() {

  }

  // 公有静态方法
  public static SingletonTest03 getSingleton() {
    //双重检测机制
    if(singleton == null) {
      synchronized (SingletonTest03.class) { // 锁住整个类
        if(singleton == null) {
          /**
           * 这里会指令重排，导致线程不安全
           * JVM指令：
               memory =allocate();    //1：分配对象的内存空间
               ctorInstance(memory);  //2：初始化对象
               instance =memory;     //3：设置instance指向刚分配的内存地址
              或者：（问题）
               memory =allocate();    //1：分配对象的内存空间
               instance =memory;     //3：设置instance指向刚分配的内存地址
               ctorInstance(memory);  //2：初始化对象
           *
           * 当线程2抢到CPU资源，线程1刚执行完1,3，线程2检测到singleton不为null，会直接返回一个null的singleton对象
           * 解决：volatile 可以禁止 JVM 的指令重排
           */
          singleton = new SingletonTest03();
        }
      }
    }
    return singleton;
  }

}
