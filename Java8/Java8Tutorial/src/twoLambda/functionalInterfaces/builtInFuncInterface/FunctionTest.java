package twoLambda.functionalInterfaces.builtInFuncInterface;

import java.util.function.Function;

public class FunctionTest {
  public static void main(String[] args) {
    //接收一个String 返回一个Integer
    Function<String,Integer> toInteger = Integer::valueOf;
    Function<String,Double> toDouble = Double::valueOf;
    Function<Integer,String> toString = String::valueOf;
    //接收一个Integer 转成String 再从String转成Double
    Function<Integer, Double> intToStrToDouble = toDouble.compose(toString);
    System.out.println(intToStrToDouble.apply(1).getClass());
    //andThen
    Function<String,String> backToString = toInteger.andThen(String::valueOf);
    System.out.println(backToString.apply("1").getClass());
  }
}
