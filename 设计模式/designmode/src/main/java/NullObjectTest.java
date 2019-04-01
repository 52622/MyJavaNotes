/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: NullObjectTest
 * Author:   copywang
 * Date:     2018/11/14 14:25
 * Description: 空对象模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class NullObjectTest {

  public static void main(String[] args) {
    AbstractOperation abstractOperation = func(-1);
    abstractOperation.request();
  }

  public static AbstractOperation func(int para) {
    if (para < 0) {
      return new NullOperation();
    }
    return new RealOperation();
  }

  public static abstract class AbstractOperation {
    abstract void request();
  }

  public static class RealOperation extends AbstractOperation {
    @Override
    void request() {
      System.out.println("do something");
    }
  }
  // 空对象实现
  public static class NullOperation extends AbstractOperation{
    @Override
    void request() {
      // do nothing
    }
  }
}
