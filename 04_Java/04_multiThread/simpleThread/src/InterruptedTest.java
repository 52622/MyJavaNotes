import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: InterruptedTest
 * Author:   copywang
 * Date:     2019/3/8 15:53
 * Description: 线程中断
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class InterruptedTest {
  //InterruptedException
  //线程处于blocking/waiting/time-waiting 调用 thread.interrupt() 会抛出 InterruptedException
  private static class MyThread1 extends Thread {
    @Override
    public void run() {
      try {
        Thread.sleep(2000);
        System.out.println("MyThread1 run");// 这一行不会被打印
      } catch (InterruptedException e) {
        e.printStackTrace();// java.lang.InterruptedException: sleep interrupted
      }
    }
  }
  //interrupted()
  private static class MyThread2 extends Thread {
    @Override
    public void run() {
      while (!interrupted()) {// 外部调用thread.interrupt()会把中断标志位置位true，从而结束当前线程
        // ..
      }
      System.out.println("MyThread2 end");
    }
  }
  //Executor 的中断操作
  //shutdown 等待线程都执行完毕之后再关闭
  //shutdownNow 调用每个线程的 interrupt() 方法

  public static void main(String[] args) {
    // eg1
    MyThread1 myThread1 = new MyThread1();
    myThread1.start();
    myThread1.interrupt();
    System.out.println("myThread1.interrupt() main end");

    // eg2
    MyThread2 myThread2 = new MyThread2();
    myThread2.start();
    myThread2.interrupt();
    System.out.println("myThread2.interrupt() main end");

    // eg3
    ExecutorService es = Executors.newCachedThreadPool();
    es.execute(()->{
      try {
        Thread.sleep(2000);
        System.out.println("ExecutorService run...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    es.shutdownNow();
    System.out.println("ExecutorService.shutdownNow() main end");

    // eg4 中断ExecutorService的一个线程
    ExecutorService es2 = Executors.newCachedThreadPool();
    Future<?> future = es2.submit(() -> {
      try {
        Thread.sleep(2000);
        System.out.println("interrupted use Future");//被中断不会打印出来
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    future.cancel(true);
    es2.shutdown();
    System.out.println("future.cancel(true) main end");
  }
}
