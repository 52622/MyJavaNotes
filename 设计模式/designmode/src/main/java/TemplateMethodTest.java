/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: TemplateMethodTest
 * Author:   copywang
 * Date:     2018/11/14 11:36
 * Description: 模板方法
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class TemplateMethodTest {
  //抽取相同的部分代码（组合步骤），然后留给子类实现
  public static void main(String[] args) {
    CaffeineBeverage caffeineBeverage = new Coffee();
    caffeineBeverage.prepareRecipe();
    System.out.println("-----------");
    caffeineBeverage = new Tea();
    caffeineBeverage.prepareRecipe();
  }

  public static abstract class CaffeineBeverage {

    final void prepareRecipe() {
      boilWater();
      brew();
      pourInCup();
      addCondiments();
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
      System.out.println("boilWater");
    }

    void pourInCup() {
      System.out.println("pourInCup");
    }
  }
  public static class Coffee extends CaffeineBeverage {
    @Override
    void brew() {
      System.out.println("Coffee.brew");
    }

    @Override
    void addCondiments() {
      System.out.println("Coffee.addCondiments");
    }
  }
  public static class Tea extends CaffeineBeverage {
    @Override
    void brew() {
      System.out.println("Tea.brew");
    }

    @Override
    void addCondiments() {
      System.out.println("Tea.addCondiments");
    }
  }
}
