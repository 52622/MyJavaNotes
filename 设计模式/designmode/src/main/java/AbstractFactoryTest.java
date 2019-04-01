/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: AbstractFactoryTest
 * Author:   copywang
 * Date:     2018/11/13 20:35
 * Description: 抽象工厂测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class AbstractFactoryTest {

  public static void main(String[] args) {
    PCFactory factory = new DellPCFactory();
    factory.createKeyboard().print();
    factory.createMouse().print();
  }

  static abstract class PCFactory {
    abstract Mouse createMouse();
    abstract Keyboard createKeyboard();
  }

  static abstract class Mouse {
    abstract void print();
  }

  static abstract class Keyboard {
    abstract void print();
  }

  static class DellMouse extends Mouse {

    @Override
    void print() {
      System.out.println("DellMouse");
    }
  }

  static class HPMouse extends Mouse {

    @Override
    void print() {
      System.out.println("HPMouse");
    }
  }

  static class DellKeyboard extends Keyboard {

    @Override
    void print() {
      System.out.println("DellKeyboard");
    }
  }

  static class HPKeyboard extends Keyboard {

    @Override
    void print() {
      System.out.println("HPKeyboard");
    }
  }

  static class DellPCFactory extends PCFactory {

    @Override
    Mouse createMouse() {
      return new DellMouse();
    }

    @Override
    Keyboard createKeyboard() {
      return new DellKeyboard();
    }
  }

  static class HPPCFactory extends PCFactory {

    @Override
    Mouse createMouse() {
      return new HPMouse();
    }

    @Override
    Keyboard createKeyboard() {
      return new HPKeyboard();
    }
  }
}
