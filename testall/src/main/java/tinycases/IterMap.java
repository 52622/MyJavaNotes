/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: IterMap
 * Author:   copywang
 * Date:     2019/3/15 15:24
 * Description: 循环链接
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package tinycases;

import java.util.HashMap;

public class IterMap {
  public static void main(String[] args) {
    HashMap<String, String> map = new HashMap<>();
    map.put("b","a");
    map.put("a","source");
    String caname = "b";
    String rename;
    do {
      rename = map.get(caname);
      if (rename != null) {
        caname = rename;
      }
    } while (rename != null);
    System.out.println(caname);
  }
}
