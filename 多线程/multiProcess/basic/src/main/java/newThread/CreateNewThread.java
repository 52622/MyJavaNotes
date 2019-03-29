package newThread;

import java.util.concurrent.*;

public class CreateNewThread {
  public static void main(String[] args) {
    // 1. 继承 Thread
    new Thread() {
      @Override
      public void run() {
        System.out.println("extend Thread");
        super.run();
      }
    }.start();

    // 2. 实现runnable接口
    new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("impl runnable");
      }
    }).start();

    // 3. 实现callable接口
    // 异步
    ExecutorService es = Executors.newSingleThreadExecutor();
    Future<String> future = es.submit(
        new Callable<String>() {
          @Override
          public String call() throws Exception {
            return "impl callable";
          }
        }
    );
    try {
      System.out.println(future.get());
      es.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
}
