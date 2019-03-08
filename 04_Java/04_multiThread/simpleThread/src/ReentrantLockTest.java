import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ReentrantLockTest
 * Author:   copywang
 * Date:     2019/3/8 16:50
 * Description: ReentrantLock
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class ReentrantLockTest {
  private Lock lock = new ReentrantLock();

  public void func() {
    lock.lock();
    try {
      for (int i = 0; i < 10; i++) {
        System.out.print(i + " ");
      }
    } finally {
      lock.unlock(); // 确保释放锁，从而避免发生死锁。
    }
  }

  public static void main(String[] args) {
    ReentrantLockTest test = new ReentrantLockTest();
    ExecutorService es = Executors.newCachedThreadPool();
    es.execute(()->test.func());
    es.execute(()->test.func());
    es.shutdown();
  }
}
