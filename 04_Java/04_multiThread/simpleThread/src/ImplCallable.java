import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ImplCallable
 * Author:   copywang
 * Date:     2019/3/8 14:58
 * Description: 实现 Callable 接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class ImplCallable implements Callable<Integer> {
  /**
   * Computes a result, or throws an exception if unable to do so.
   *
   * @return computed result
   * @throws Exception if unable to compute a result
   */
  @Override
  public Integer call() throws Exception {
    return 1;
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 返回值用FutureTask封装
    FutureTask<Integer> task = new FutureTask<>(new ImplCallable());
    new Thread(task).start();
    System.out.println(task.get());

    // lambda
    FutureTask<String> what = new FutureTask<String>(() -> {
      return "what";
    });
    new Thread(what).start();
    System.out.println(what.get());
  }
}
