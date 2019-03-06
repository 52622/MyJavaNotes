/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: A
 * Author:   copywang
 * Date:     2019/3/5 14:49
 * Description: 重载测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class A {
  public String show(D obj) {
    return ("A and D");
  }

  public String show(A obj) {
    return ("A and A");
  }
}
