import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ThreadCooperTest
 * Author:   copywang
 * Date:     2019/3/8 16:56
 * Description: 线程协作测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class ThreadCooperTest {
  // join()
  // 一个线程调用另外一个线程的join()方法，需要等待那个线程执行完毕之后再接着执行本身线程
  private class A extends Thread {
    @Override
    public void run() {
      System.out.println("A");
    }
  }

  private class B extends Thread {

    private A a;

    B(A a) {
      this.a = a;
    }

    @Override
    public void run() {
      try {
        a.join();//调用了a的join方法，需要等待a结束后再接着往下执行
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("B");
    }
  }

  public void test() {
    A a = new A();
    B b = new B(a);
    b.start();//B先启动
    a.start();
  }

  //wait() notify() notifyAll() --- Object
  //只能用在同步方法或者同步控制块中使用，否则会在运行时抛出 IllegalMonitorStateException
  //wait()会释放锁
  synchronized void before() {
    System.out.println("before");
    notifyAll();//获取锁，唤醒wait的线程
  }

  synchronized void after() {
    try {
      wait();//等待其他线程唤醒
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("after");
  }

  //await() signal() signalAll()
  //juc Condition
  private Lock lock = new ReentrantLock();
  private Condition condition = lock.newCondition();

  void before1() {
    lock.lock();
    try {
      System.out.println("before");
      condition.signalAll();
    } finally {
      lock.unlock();
    }
  }

  void after1() {
    lock.lock();
    try {
      condition.await();
      System.out.println("after");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    // eg1 join()
    ThreadCooperTest test = new ThreadCooperTest();
    test.test();

    // eg2 wait()
    ExecutorService es = Executors.newCachedThreadPool();
    es.execute(() -> test.after());
    es.execute(() -> test.before());
    es.shutdown();

    // eg3
    es = Executors.newCachedThreadPool();
    es.execute(() -> test.after1());
    es.execute(() -> test.before1());
    es.shutdown();
  }

}
