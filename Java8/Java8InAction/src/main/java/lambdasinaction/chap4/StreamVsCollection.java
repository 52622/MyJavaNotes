package lambdasinaction.chap4;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class StreamVsCollection {

  public static void main(String... args) {
    List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
    Stream<String> s = names.stream();
    s.forEach(System.out::println);
    // uncommenting this line will result in an IllegalStateException
    // because streams can be consumed only once
    // 流只能是单向的处理一次，如果再次执行下面的语句，会抛出 IllegalStateException
    // Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
    // s.forEach(System.out::println);
  }
}