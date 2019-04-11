package lambdasinaction.chap5;

import lambdasinaction.chap4.*;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap4.Dish.menu;

public class Mapping {

  /**
   * 功能描述:
   * http://www.java67.com/2016/03/how-to-use-flatmap-in-java-8-stream.html
   */
  public static void main(String... args) {

    // map
    // 普通的String对象
    List<String> dishNames = menu.stream()
        .map(Dish::getName)
        .collect(toList());
    System.out.println(dishNames);

    // map
    // 使用String对象的方法推导
    List<String> words = Arrays.asList("Hello", "World");
    List<Integer> wordLengths = words.stream()
        .map(String::length)
        .collect(toList());
    System.out.println(wordLengths);

    // flatMap
    // 扁平化流 Stream<String> 都变成单个元素
    words.stream()
        .flatMap((String line) -> Arrays.stream(line.split("")))
        .distinct()
        .forEach(System.out::println);

    // flatMap
    List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
    List<Integer> numbers2 = Arrays.asList(6, 7, 8);
    List<int[]> pairs =
        numbers1.stream()
            .flatMap((Integer i) -> numbers2.stream()
                .map((Integer j) -> new int[]{i, j})
            )
            .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
            .collect(toList());
    pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));

    Stream<int[]> stream = numbers1.stream()
        .flatMap((Integer i) -> numbers2.stream()
            .map((Integer j) -> new int[]{i, j})
        );
    System.out.println("==========");
    List<int[]> collect = numbers1.stream()
        .flatMap((Integer i) -> numbers2.stream()
            .map((Integer j) -> new int[]{i, j})
        ).collect(toList());
    System.out.println("==========");
    for (int[] i : collect) {
      System.out.println(i[0] + ":" + i[1]);
    }
  }
}
