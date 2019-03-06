/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Problems
 * Author:   copywang
 * Date:     2019/3/5 17:14
 * Description: 泛型问题
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package FanXinCaChu;

import java.util.ArrayList;
import java.util.List;

public class Problems {

  public static void main(String[] args) {
    // eg1 不支持泛型数组
//  List<Integer>[] arrayOfLists = new List<Integer>[2]; //Generic array creation
    // eg1.1 字符串数组不能存Integer
    Object[] strings = new String[2];
    strings[0] = "hi";   // OK
//    strings[1] = 100;    // java.lang.ArrayStoreException: java.lang.Integer
    //eg1.2
    /**
     假设我们支持泛型数组的创建，由于运行时期类型信息已经被擦除，JVM实际上根本就不知道new ArrayList<String>()和new ArrayList<Integer>()的区别

     Object[] stringLists = new List<String>[];  // compiler error, but pretend it's allowed
     stringLists[0] = new ArrayList<String>();   // OK
     stringLists[1] = new ArrayList<Integer>(); // An ArrayStoreException should be thrown, but the runtime can't detect it.
     Class c1 = new ArrayList<String>().getClass();
     Class c2 = new ArrayList<Integer>().getClass();
     System.out.println(c1 == c2); // true
     */

    // eg2
    
  }
}
