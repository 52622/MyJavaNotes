/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ExtThread
 * Author:   copywang
 * Date:     2019/3/8 15:02
 * Description: 继承Thread类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class ExtThread extends Thread {
  @Override
  public void run() {
    System.out.println("ExtThread run()");
  }

  public static void main(String[] args) {
    new ExtThread().start();
  }
}
