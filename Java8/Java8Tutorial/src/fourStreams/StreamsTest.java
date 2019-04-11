package fourStreams;

import java.util.Arrays;
import java.util.List;

/**
 * java.util.Stream 表示能应用在一组元素上一次执行的操作序列
 * Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，而中间操作返回Stream本身，这样你就可以将多个操作依次串起来
 * Stream 的创建需要指定一个数据源，比如 java.util.Collection 的子类，List 或者 Set， Map 不支持。Stream 的操作可以串行执行或者并行执行
 * Collection.stream() 或者 Collection.parallelStream() 来创建一个Stream
 */
public class StreamsTest {
  public static void main(String[] args) {
    //数据源
    List<String> list = Arrays.asList("aaa1", "bbb1", "ccc1", "aaa2", "bbb2", "ccc2", "aaa3", "bbb3", "ccc3");
    //Filter 过滤 中间操作 predicate接口
    System.out.println("Filter");
    list.stream().filter((s)->s.startsWith("a")).forEach(System.out::println);
    //Sorted 排序 中间操作 自定义的 Comparator 或者 使用默认排序
    System.out.println("Sorted");
    list.stream().sorted().forEach(System.out::println);
    //Map 映射 中间操作 将元素根据指定的 Function 接口来依次将元素转成另外的对象
    System.out.println("Map");
    list.stream().map((String::toUpperCase)).forEach(System.out::println);
    //Match 匹配 最终操作 允许检测指定的Predicate是否匹配整个Stream，返回一个 boolean 类型的值
    System.out.println("Match");
    boolean allStartWith = list.stream().allMatch(s -> s.startsWith("a"));
    System.out.println(allStartWith);
    //Count 计数 最终操作 返回Stream中元素的个数
    System.out.println("Count");
    long count = list.stream().filter((s) -> s.startsWith("a")).count();
    System.out.println(count);
  }
}
