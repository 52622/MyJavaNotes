/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Box
 * Author:   copywang
 * Date:     2019/3/5 14:36
 * Description: 泛型类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class Box<T> {
  private T t;

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }
}
