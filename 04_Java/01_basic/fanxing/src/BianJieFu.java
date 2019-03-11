/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: BianJieFu
 * Author:   copywang
 * Date:     2019/3/5 14:45
 * Description: 边界符
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class BianJieFu {
//  告诉编译器类型参数T代表的都是实现了Comparable接口的类，这样等于告诉编译器它们都至少实现了compareTo方法
  public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
    int count = 0;
    for (T e : anArray)
      if (e.compareTo(elem) > 0)
        ++count;
    return count;
  }
}
