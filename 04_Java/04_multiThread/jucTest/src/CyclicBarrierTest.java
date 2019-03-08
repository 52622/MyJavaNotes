import java.util.concurrent.*;

public class CyclicBarrierTest {
    //CyclicBarrier 循环屏障
    //线程执行 await() 方法之后计数器会减 1，并进行等待，直到计数器为 0，所有调用 await() 方法而在等待的线程才能继续执行
    //调用 reset() 方法可以循环使用
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            es.execute(()->{
                System.out.println("before");
                int await = 0;
                try {
                    await = barrier.await();//在这里等待，每次调用计数器减一，到0时所有线程继续执行
                    System.out.println(await);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("after");//继续执行
            });
        }

        MyRun myRun = new MyRun();
        CyclicBarrier barrier1 = new CyclicBarrier(3, myRun);
        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                System.out.println("before");
                try {
                    int await = barrier1.await();
                    System.out.println(await);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();//到达内存屏障之后 最后一个线程会在执行一下传入的这个任务
        }
    }
    private static class MyRun implements Runnable {

        @Override
        public void run() {
            System.out.println("MyRun...");
        }
    }
}
