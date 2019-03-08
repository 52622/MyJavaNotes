import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MyThreadPool
 * Author:   copywang
 * Date:     2019/3/8 15:05
 * Description: 基础线程池
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class MyThreadPool {
  public static void main(String[] args) {
    //Executor
    /**
     * CachedThreadPool：一个任务创建一个线程；
     * FixedThreadPool：所有任务只能使用固定大小的线程；
     * SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。
     */
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
      executorService.execute(()->{
        System.out.println("test");
      });
    }
    executorService.shutdown();

    //Daemon 守护线程
    //main() 属于非守护线程
    //Thread setDaemon() 方法将一个线程设置为守护线程

    //Thread.sleep(millisec) 方法会休眠当前正在执行的线程
    //sleep() 可能会抛出 InterruptedException，因为异常不能跨线程传播回 main() 中，因此必须在本地进行处理

    //Thread.yield() 的调用声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其它线程来执行

  }
}
