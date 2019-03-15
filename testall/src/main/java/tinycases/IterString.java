/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: IterString
 * Author:   copywang
 * Date:     2019/3/15 14:24
 * Description: 迭代去重
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package tinycases;

public class IterString {
  public static void main(String[] args) {
    String test = "&&&123";
    System.out.println(test(test));
  }
  public static String test(String before) {
    while(before.startsWith("&")) {
      before = before.substring("&".length());
    }
    return before;
  }
}
