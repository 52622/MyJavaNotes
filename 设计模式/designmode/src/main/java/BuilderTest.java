import lombok.Data;

/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: BuilderTest
 * Author:   copywang
 * Date:     2018/11/13 20:46
 * Description: 建造者模式测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class BuilderTest {

  public static void main(String[] args) {
    ReallyBuilder builder = new ReallyBuilder();
    Director director = new Director(builder);
    director.build();
    System.out.println(builder.getResult());// 3
  }

  // 建造者角色
  static abstract class Builder {
    // 建造方法
    abstract void part1();
    abstract void part2();
    abstract void part3();
  }

  // 监工
  static class Director {
    private Builder builder; // 面向接口编程

    public Director(Builder builder) {
      this.builder = builder;
    }

    public Builder getBuilder() {
      return builder;
    }

    public void setBuilder(Builder builder) {
      this.builder = builder;
    }

    public void build() { // 对外隐藏了构建细节
      builder.part1();
      builder.part2();
      builder.part3();
    }
  }

  // 具体的建造者
  static class ReallyBuilder extends Builder {

    private int count;

    @Override
    void part1() {
      count += 1;
    }

    @Override
    void part2() {
      count += 1;
    }

    @Override
    void part3() {
      count += 1;
    }

    //最终建造出来的对象
    public int getResult() {
      return count;
    }
  }

}
