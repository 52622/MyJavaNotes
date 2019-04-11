package threeOptionals;

import java.util.Optional;

//Optional 是一个简单的容器，其值可能是null或者不是null

public class OptionalTest {
  public static void main(String[] args) {
    //of（）：为非null的值创建一个Optional
    Optional<String> optional = Optional.of("bam");
    // isPresent（）： 如果值存在返回true，否则返回false
    optional.isPresent();           // true
    //get()：如果Optional有值则将其返回，否则抛出NoSuchElementException
    optional.get();                 // "bam"
    //orElse（）：如果有值则将其返回，否则返回指定的其它值
    optional.orElse("fallback");    // "bam"
    //ifPresent（）：如果Optional实例有值则为其调用consumer，否则不做处理
    optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
  }
  //https://blog.kaaass.net/archives/764
  public static char getFirstChar(String s) {
    if (s==null) {
      return 'a';
    }
    return s.charAt(0);
  }
  //链式调用替换上面的null判断
  public static char getFirstCharWithOptional(String s) {
    return Optional.ofNullable(s).map(string -> string.charAt(0)).orElse('a');
  }
}
