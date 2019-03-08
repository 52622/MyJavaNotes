import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SyncTest
 * Author:   copywang
 * Date:     2019/3/8 16:16
 * Description: 互斥同步
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SyncTest {
  //同步代码块
  //只作用于同一个实例
  public void func1() {
    synchronized (this) {
      for (int i = 0; i < 10; i++) {
        System.out.print(i + ",");
      }
    }
  }

  //同步方法 = 同步代码块
  //只作用于同一个实例
  public synchronized void func2() {
      for (int i = 0; i < 10; i++) {
        System.out.print(i + ",");
    }
  }

  //同步类
  //作用于类的所有实例
  public void func3() {
    synchronized (SyncTest.class) {
      for (int i = 0; i < 10; i++) {
        System.out.print(i + " ");
      }
    }
  }

  //同步静态方法 = 同步类
  //作用于类的所有实例
  public synchronized static void func4() {
    for (int i = 0; i < 10; i++) {
      System.out.print(i + ",");
    }
  }

  public static void main(String[] args) {
    System.out.println("=====================================");
    //eg1
    //0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,
    //同个实例第二个线程要等待
    SyncTest test = new SyncTest();
    ExecutorService es1 = Executors.newCachedThreadPool();
    es1.execute(() -> test.func1());
    es1.execute(() -> test.func1());
    es1.shutdown();
    //eg2
    //不同实例不需要等待
    SyncTest test1 = new SyncTest();
    SyncTest test2 = new SyncTest();
    ExecutorService es2 = Executors.newFixedThreadPool(2);
    es2.submit(() -> test1.func1());
    es2.submit(() -> test2.func1());
    es2.shutdown();
  }
}
