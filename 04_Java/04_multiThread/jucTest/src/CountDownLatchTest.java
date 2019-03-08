import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CountDownLatchTest
 * Author:   copywang
 * Date:     2019/3/8 17:23
 * Description: 一个线程等待多个线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class CountDownLatchTest {
    //维护了一个计数器 cnt，每次调用 countDown() 方法会让计数器的值减 1，减到 0 的时候，那些因为调用 await() 方法而在等待的线程就会被唤醒
    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(10);
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(()->{
            try {
                count.await();
                System.out.printf("before");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < 10; i++) {
            es.execute(()->{
                System.out.println("run...");
                count.countDown();
            });
        }
        es.shutdown();
    }
}
