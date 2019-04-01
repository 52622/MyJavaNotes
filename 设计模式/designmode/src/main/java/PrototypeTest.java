/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: PrototypeTest
 * Author:   copywang
 * Date:     2018/11/13 22:35
 * Description: 原型模式测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class PrototypeTest {

  public static void main(String[] args) {
    ConcretePrototype prototype = new ConcretePrototype("xxx");
    Prototype clone = prototype.myClone();
    System.out.println(clone.toString());
  }

  static abstract class Prototype {
    abstract Prototype myClone();
  }

  static class ConcretePrototype extends Prototype {

    private String filed;

    public ConcretePrototype(String filed) {
      this.filed = filed;
    }

    @Override
    Prototype myClone() {
      return new ConcretePrototype(filed);
    }

    @Override
    public String toString() {
      return filed;
    }
  }

}
