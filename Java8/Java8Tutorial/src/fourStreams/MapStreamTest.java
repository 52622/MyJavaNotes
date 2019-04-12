package fourStreams;

import java.util.HashMap;
import java.util.Map;

//Map接口本身没有可用的 stream（）方法
//可以在键，值上创建专门的流或者通过 map.keySet().stream(),map.values().stream()和map.entrySet().stream()
public class MapStreamTest {
  public static void main(String[] args) {
    Map<Integer, String> map = new HashMap<>();

    for (int i = 0; i < 10; i++) {
      map.putIfAbsent(i, "val" + i);
    }

    map.forEach((id, val) -> System.out.println(val));

    //map计算
    map.computeIfPresent(3, (num, val) -> val + num);
    map.get(3);             // val33

    map.computeIfPresent(9, (num, val) -> null);
    map.containsKey(9);     // false

    map.computeIfAbsent(23, num -> "val" + num);
    map.containsKey(23);    // true

    map.computeIfAbsent(3, num -> "bam");
    map.get(3);             // val33

    //在Map里删除一个键值全都匹配的项
    map.remove(3, "val3");
    map.get(3);             // val33
    map.remove(3, "val33");
    map.get(3);             // null

    map.getOrDefault(42, "not found");  // not found

    //Map的元素做合并
    //Merge 做的事情是如果键名不存在则插入，否则则对原键对应的值做合并操作并重新插入到map中
    map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
    map.get(9);             // val9
    map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
    map.get(9);             // val9concat
  }
}
