import java.util.Arrays;

/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: MyAbstractStringBuilder
 * Author:   copywang
 * Date:     2018/11/13 22:03
 * Description: 自定义实现StringBuilder
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class MyAbstractStringBuilder {
  protected char[] value;
  protected int count; // 索引

  public MyAbstractStringBuilder(int capacity) {
    count = 0;
    value = new char[capacity];
  }

  //每次添加一个字符，索引+1
  //最小的长度
  public MyAbstractStringBuilder append(char c) {
    ensureCapacityInternal(count + 1);//数组的长度保证够再存1个字符
    value[count++] = c;
    return this;
  }

  private void ensureCapacityInternal(int minCapacity) {
    if (minCapacity > value.length) {//如果数组长度不够，就扩容
      expandCapacity(minCapacity);
    }
  }

  private void expandCapacity(int minCapacity) {
    int newCapacity = (value.length + 1) * 2;//扩容为当前+1的两倍

    if (newCapacity < minCapacity) {
      newCapacity = minCapacity;
    }
    // 处理内存溢出
    if (newCapacity < 0) {
      if (minCapacity < 0)
        throw new OutOfMemoryError();
      newCapacity = Integer.MAX_VALUE;//最大值
    }
    value = Arrays.copyOf(value,newCapacity);//拷贝到一个新的字符数组
  }
}
