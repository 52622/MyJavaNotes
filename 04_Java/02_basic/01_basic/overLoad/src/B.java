/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: B
 * Author:   copywang
 * Date:     2019/3/5 14:49
 * Description: 重载测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class B extends A {
  public String show(B obj) {
    return ("B and B");
  }

  public String show(A obj) {
    return ("B and A");
  }
}
