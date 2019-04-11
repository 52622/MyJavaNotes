package lambdasinaction.chap5;

import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class BuildingStreams {

  /**
   * 功能描述:
   * <p>
   * 获取steam流的多种方式
   */
  public static void main(String... args) throws Exception {

    // Stream.of
    // 从数组中获取流
    Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
    stream.map(String::toUpperCase).forEach(System.out::println);

    // Stream.empty
    // 一个空的流
    Stream<String> emptyStream = Stream.empty();

    // Arrays.stream
    int[] numbers = {2, 3, 5, 7, 11, 13};
    System.out.println(Arrays.stream(numbers).sum());

    // Stream.iterate
    // 无限序列一定要截断
    Stream.iterate(0, n -> n + 2) // 按照一个规则生产一个无限序列
        .limit(10) // 截断，只取前面10个元素
        .forEach(System.out::println);

    // fibonnaci with iterate
    Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
        .limit(10)
        .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));

    Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
        .limit(10)
        .map(t -> t[0])
        .forEach(System.out::println);

    // random stream of doubles with Stream.generate
    Stream.generate(Math::random)
        .limit(10)
        .forEach(System.out::println);

    // stream of 1s with Stream.generate
    IntStream.generate(() -> 1)
        .limit(5)
        .forEach(System.out::println);

    IntStream.generate(new IntSupplier() {
      public int getAsInt() {
        return 2;
      }
    }).limit(5)
        .forEach(System.out::println);


    IntSupplier fib = new IntSupplier() {
      private int previous = 0;
      private int current = 1;

      public int getAsInt() {
        int nextValue = this.previous + this.current;
        this.previous = this.current;
        this.current = nextValue;
        return this.previous;
      }
    };
    IntStream.generate(fib).limit(10).forEach(System.out::println);

    //flatMap 扁平化map
    //map 得到一个steam
    //https://blog.csdn.net/Mark_Chao/article/details/80810030
    long uniqueWords;

    List<String> stream1 = Files.lines(Paths.get("src/main/resources/lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
        .flatMap(line -> Arrays.stream(line.split(" "))).collect(Collectors.toList());

    List<Stream<String>> stream2 = Files.lines(Paths.get("src/main/resources/lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
        .map(line -> Arrays.stream(line.split(" "))).collect(Collectors.toList());

    //和直接使用flatMap一致
    List<String> collect = Files.lines(Paths.get("src/main/resources/lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
        .map(line -> line.split(" ")).flatMap(Arrays::stream).collect(Collectors.toList());
//        .distinct()
//        .count();

//    System.out.println("There are " + uniqueWords + " unique words in data.txt");


  }
}
