package twoLambda.functionalInterfaces.builtInFuncInterface;

import java.util.Objects;
import java.util.function.Predicate;

public class PredicateTest {
  public static void main(String[] args) {
    Predicate<String> pre = (s) -> s.length() > 0;
    pre.test("foo");
    pre.negate().test("foo");

    //test方法的引用都指向了右边的方法，返回true或者false
    Predicate<Boolean> nonNull = Objects::nonNull;
    Predicate<Boolean> isNull = Objects::isNull;

    Predicate<String> isEmpty = String::isEmpty;
    Predicate<String> isNotEmpty = isEmpty.negate();
  }
}
