package Synchronized;

public class NotSyncSample implements Runnable {
  private static int count = 0;

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new Thread(new NotSyncSample()).start();
    }
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("result: " + count);
  }
  @Override
  public void run() {
    for (int i = 0; i < 100000; i++) {
      count++;
    }
  }
}
