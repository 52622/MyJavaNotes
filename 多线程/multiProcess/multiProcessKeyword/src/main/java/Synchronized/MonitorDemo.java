package Synchronized;

public class MonitorDemo {
  private int a = 0;

  //1-2-3-4-5-6
  //2 happens-before 5
  //3 happens-before 4
  public synchronized void writer() {     // 1 获取锁
    a++;                                // 2 临界区代码
  }                                       // 3 释放锁

  public synchronized void reader() {    // 4 获取锁
    int i = a;                         // 5 临界区代码
  }                                      // 6 释放锁
}
