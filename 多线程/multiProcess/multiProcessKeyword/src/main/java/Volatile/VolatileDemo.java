package Volatile;

public class VolatileDemo {
  private static volatile boolean isOver = false;

  public static void main(String[] args) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (!isOver) ;
      }
    });
    thread.start();
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    isOver = true;//执行到这里thread的工作内存的isOver会失效，可以结束循环
  }
}
