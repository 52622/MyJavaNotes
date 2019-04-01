/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: SimpleFactoryTest
 * Author:   copywang
 * Date:     2018/11/13 19:47
 * Description: 简单工厂模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SimpleFactoryTest {

  public static void main(String[] args) {
    SimpleFactory factory = new SimpleFactory();
    factory.createProduct(1).print();
    factory.createProduct(2).print();
  }

  static class SimpleFactory {
    public Product createProduct(int type) {
      if (type == 1) {
        return new A();
      } else if (type == 2) {
        return new B();
      }
      return null;
    }
  }

  interface Product {

    void print();
  }

  static class A implements Product {

    @Override
    public void print() {
      System.out.println("A");
    }
  }

  static class B implements Product {

    @Override
    public void print() {
      System.out.println("B");
    }
  }

}
