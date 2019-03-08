import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    /**
     * FIFO 队列 ：LinkedBlockingQueue、ArrayBlockingQueue（固定长度）
     * 优先级队列 ：PriorityBlockingQueue
     */
    //take() put()

    public static void main(String[] args) {
        BlockingQueue queue = new ArrayBlockingQueue<>(5);

        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                try {
                    System.out.println("taking");
                    queue.take();
                    System.out.println("taked");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    System.out.println("producing");
                    queue.put("moon");
                    System.out.println("produced");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
