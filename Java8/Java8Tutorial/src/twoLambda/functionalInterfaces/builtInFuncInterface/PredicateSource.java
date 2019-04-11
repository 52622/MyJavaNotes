package twoLambda.functionalInterfaces.builtInFuncInterface;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
//Predicate 接口是只有一个参数的返回布尔类型值的 断言型 接口。该接口包含多种默认方法来将 Predicate 组合成其他复杂的逻辑（比如：与，或，非）
//这里是Predicate 接口源码，为了方便注释，直接拎出来一部分代码做解释
public interface PredicateSource<T> {
  // 该方法是接受一个传入类型,返回一个布尔值.此方法应用于判断.
  boolean test(T t);

  //and方法与关系型运算符"&&"相似，两边都成立才返回true
  default Predicate<T> and(Predicate<? super T> other) {
    Objects.requireNonNull(other);
    return (t) -> test(t) && other.test(t);
  }
  // 与关系运算符"!"相似，对判断进行取反
  default Predicate<T> negate() {
    return (t) -> !test(t);
  }
  //or方法与关系型运算符"||"相似，两边只要有一个成立就返回true
  default Predicate<T> or(Predicate<? super T> other) {
    Objects.requireNonNull(other);
    return (t) -> test(t) || other.test(t);
  }
  // 该方法接收一个Object对象,返回一个Predicate类型.此方法用于判断第一个test的方法与第二个test方法相同(equal).
  static <T> Predicate<T> isEqual(Object targetRef) {
    return (null == targetRef)
        ? Objects::isNull
        : object -> targetRef.equals(object);
  }
}
