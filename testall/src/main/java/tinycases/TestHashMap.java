package tinycases;

import java.util.HashMap;

public class TestHashMap {
  private static HashMap< Integer, Integer > map = new HashMap<>(2);

  public static void main(String[] args) throws InterruptedException {
    //线程1
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 1; i <= 100000; i++) {
          int result = i;
          new Thread(new Runnable() {
            @Override
            public void run() {
              map.put(result, result);
            }
          }, "ftf" + i).start();
        }


      }
    });

    t1.start();


    //让主线程睡眠5秒，保证线程1和线程2执行完毕
    Thread.sleep(5000);
    for (int i= 1; i <= 100000; i++) {
      //检测数据是否发生丢失
      Integer value = map.get(i);
      if (value==null) {
        System.out.println(i + "数据丢失");
      }
    }

    System.out.println("end...");

  }
}

