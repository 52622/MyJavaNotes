package ThreadCommunicate;

public class ThreadInterrupted {
  public static void main(String[] args) throws InterruptedException {
    Thread sleepThread = new Thread() {
      @Override
      public void run() {
        try {
          Thread.sleep(1000);
          System.out.println("sleepThread end... this will not be printed");
        } catch (InterruptedException e) {// 抛出InterruptedException后清除标志位
          e.printStackTrace();
        }
        super.run();
      }
    };
    Thread whileThread = new Thread() {
      @Override
      public void run() {
        while(true);
      }
    };
    Thread busyThread = new Thread() {
      @Override
      public void run() {
        while(!interrupted()) {//在循环体中使用 interrupted() 方法来判断线程是否处于中断状态，从而提前结束线程

        };
        System.out.println("busyThread exit");
      }
    };
    sleepThread.start();
    whileThread.start();
    busyThread.start();
    sleepThread.interrupt();//中断线程
    whileThread.interrupt();//中断线程，但是线程不会结束
    busyThread.interrupt();//中断线程
    while (sleepThread.isInterrupted()) ;//持续监测sleepThread，一旦sleepThread的中断标志位清零，即sleepThread.isInterrupted()返回为false时才会继续Main线程才会继续往下执行
    System.out.println("sleepThread isInterrupted: " + sleepThread.isInterrupted());//false
    System.out.println("whileThread isInterrupted: " + whileThread.isInterrupted());//不会清除标志位
    System.out.println("busyThread isInterrupted: " + busyThread.isInterrupted());//会清除标志位

  }
}
