/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: AdapterPatternTest
 * Author:   copywang
 * Date:     2018/11/14 14:42
 * Description: 适配器测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class AdapterPatternTest {
  /**
   * 把B转换成A
   * A -> a()
   * B -> b()
   * Adapter(B) implements A -> a() -> B.b()
   *
   * Adapter 表面上是 A 的实现类，调用 a() 方法，但是由于传进去了B作为构造方法的参数，调用 a() 的时候其实是调用 B.b()
   * 这里B伪装成了A
   */
  public static void main(String[] args) {
    Turkey turkey = new WildTurkey();
    Duck duck = new TurkeyAdapter(turkey);
    duck.quack();
  }

  public interface Duck {
    void quack();
  }
  public interface Turkey {
    void gobble();
  }
  public static class WildTurkey implements Turkey {
    @Override
    public void gobble() {
      System.out.println("gobble!");
    }
  }
  public static class TurkeyAdapter implements Duck {
    Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
      this.turkey = turkey;
    }

    @Override
    public void quack() {
      turkey.gobble();
    }
  }
}
