package twoLambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringSortTest {
  public static void main(String[] args) {
    //老版的排序
    List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
    Collections.sort(names, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return o2.compareTo(o1);
      }
    });
    for (String name:
         names) {
      System.out.println(name);
    }
    //Java8 lambda sort
    Collections.sort(names,(String o1,String o2)->{
      return o2.compareTo(o1);
    });
    //or
    Collections.sort(names,(String o1,String o2)-> o2.compareTo(o1));
    //只有一行代码的函数体可以写成
    //类型可以自动推导
    Collections.sort(names,(o1,o2)->o2.compareTo(o1));
  }
}
