package twoLambda.functionalInterfaces;

public class FunctionalInterfaceTest {
  //函数式接口(Functional Interfaces)
  //只包含一个抽象方法,但是可以有多个非抽象方法(也就是上面提到的默认方法)的接口，可以被隐式转换为lambda表达式
  //java.lang.Runnable 与 java.util.concurrent.Callable 和 java.util.function包下
  //注解@FunctionalInterface
  //只要接口只包含一个抽象方法，虚拟机会自动判断该接口为函数式接口
  public static void main(String[] args) {
    //String to Integer
    Converter<String,Integer> converter = (from) -> Integer.valueOf(from);
    Integer target = converter.convert("123");
    System.out.println(target);
    System.out.println(target.getClass());
  }
}
