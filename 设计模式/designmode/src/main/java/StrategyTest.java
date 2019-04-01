/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: StrategyTest
 * Author:   copywang
 * Date:     2018/11/14 11:32
 * Description: 策略模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class StrategyTest {

  public static void main(String[] args) {
    Duck duck = new Duck();
    duck.setQuackBehavior(new Squeak());
    duck.performQuack();
    duck.setQuackBehavior(new Quack());
    duck.performQuack();
  }

  public interface QuackBehavior {
    void quack();
  }
  public static class Quack implements QuackBehavior {
    @Override
    public void quack() {
      System.out.println("quack!");
    }
  }
  public static class Squeak implements QuackBehavior{
    @Override
    public void quack() {
      System.out.println("squeak!");
    }
  }
  public static class Duck {

    private QuackBehavior quackBehavior;

    public void performQuack() {
      if (quackBehavior != null) {
        quackBehavior.quack();
      }
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
      this.quackBehavior = quackBehavior;
    }
  }
}
