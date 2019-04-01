/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: MyStringBuilderImpl
 * Author:   copywang
 * Date:     2018/11/13 22:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class MyStringBuilderImpl extends MyAbstractStringBuilder {
  public MyStringBuilderImpl() {
    super(16);
  }

  @Override
  public String toString() {
    return new String(value,0,count);//原数组的拷贝
  }
}
