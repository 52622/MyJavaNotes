package twoLambda.functionalInterfaces.builtInFuncInterface;

import java.util.function.Consumer;

public class ConsumersTest {
  public static void main(String[] args) {
    //打印String
    Consumer<String> s = (p) -> System.out.println(p);
    s.accept("hello world");
    //打印2次
    Consumer<String> i = (p) -> System.out.println(p);
    Consumer<String> andThen = i.andThen((p) -> System.out.println(p));
    andThen.accept("1");
  }
}
