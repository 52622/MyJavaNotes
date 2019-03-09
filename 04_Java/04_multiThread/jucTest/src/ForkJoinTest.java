import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest extends RecursiveTask<Integer> {
    //把大的计算任务拆分成多个小任务并行计算
    //工作窃取算法
    //每个线程都维护了一个双端队列，用来存储需要执行的任务
    //允许空闲的线程从其它线程的双端队列中窃取一个任务来执行。窃取的任务必须是最晚的任务，避免和队列所属线程发生竞争
    //如果队列中只有一个任务时还是会发生竞争
    private final int threshold = 5;
    private int first;
    private int last;

    public ForkJoinTest(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first <= threshold) {
            // 任务足够小则直接计算
            for (int i = first; i <= last; i++) {
                result += i;
            }
        } else {
            // 拆分成小任务
            int middle = first + (last - first) / 2;
            ForkJoinTest leftTask = new ForkJoinTest(first, middle);
            ForkJoinTest rightTask = new ForkJoinTest(middle + 1, last);
            leftTask.fork();
            rightTask.fork();
            result = leftTask.join() + rightTask.join();
        }
        return result;
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinTest example = new ForkJoinTest(1, 10000);
        ForkJoinPool forkJoinPool = new ForkJoinPool();//线程数量取决于 CPU 核数
        Future result = forkJoinPool.submit(example);
        System.out.println(result.get());
    }
}
