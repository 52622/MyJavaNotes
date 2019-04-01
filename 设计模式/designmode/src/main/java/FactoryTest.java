/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: FactoryTest
 * Author:   copywang
 * Date:     2018/11/13 16:49
 * Description: 工厂模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class FactoryTest {
  public static void main(String[] args) {
    ProductFactory pf = new AProductFactory();
    pf.createProduct().print();
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

  interface ProductFactory {
    Product createProduct();
  }

  static class AProductFactory implements ProductFactory {

    @Override
    public Product createProduct() {
      return new A();
    }
  }

  static class BProductFactory implements ProductFactory {

    @Override
    public Product createProduct() {
      return new B();
    }
  }
}
