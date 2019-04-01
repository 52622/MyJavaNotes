/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: MyStringBuilderTest
 * Author:   copywang
 * Date:     2018/11/13 22:28
 * Description: 测试自定义的StringBuilder
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
// https://www.cnblogs.com/godtrue/p/8641891.html
public class MyStringBuilderTest {
  public static void main(String[] args) {
    MyStringBuilderImpl builder = new MyStringBuilderImpl();
    for (int i = 0; i < 10; i++) {
      builder.append((char) ('0' + i));
    }
    System.out.println(builder.toString());
  }
}
