/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestABCD
 * Author:   copywang
 * Date:     2019/3/5 14:50
 * Description: 重载测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class TestABCD {
  public static void main(String[] args) {
    A a1 = new A();
    A a2 = new B();
    B b = new B();
    C c = new C();
    D d = new D();
    /**
     * 涉及到重写时，方法调用的优先级为：
     *
     * this.show(O)
     * super.show(O)
     * this.show((super)O)
     * super.show((super)O)
     */
    System.out.println(a1.show(b)); // A and A
    System.out.println(a1.show(c)); // A and A
    System.out.println(a1.show(d)); // A and D
    System.out.println(a2.show(b)); // B and A
    System.out.println(a2.show(c)); // B and A
    System.out.println(a2.show(d)); // A and D
    System.out.println(b.show(b));  // B and B
    System.out.println(b.show(c));  // B and B
    System.out.println(b.show(d));  // A and D
  }
}
