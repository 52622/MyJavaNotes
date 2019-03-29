package ThreadCommunicate;

public class ThreadJoin {
  public static void main(String[] args) {
    Thread pre = Thread.currentThread();
    for (int i = 0; i < 10; i++) {
      Thread cur = new JoinThread(pre);
      cur.start();
      pre = cur;
    }
  }
  static class JoinThread extends Thread {
    private Thread thread;

    public JoinThread(Thread thread) {
      this.thread = thread;
    }

    @Override
    public void run() {
      try {
        thread.join();
        System.out.println(thread.getName() + " end");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
