/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ImplRunnable
 * Author:   copywang
 * Date:     2019/3/8 14:56
 * Description: 实现 Runnable 接口
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class ImplRunnable implements Runnable {
  public static void main(String[] args) {
    new Thread(new ImplRunnable()).start();
    // lambda
    new Thread(()->{
      System.out.println("lambda");
    }).start();
  }

  @Override
  public void run() {
    System.out.println("ImplRunnable");
  }
}
