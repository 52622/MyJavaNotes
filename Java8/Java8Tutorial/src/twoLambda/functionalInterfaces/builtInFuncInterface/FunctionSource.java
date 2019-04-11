package twoLambda.functionalInterfaces.builtInFuncInterface;

import java.util.Objects;
import java.util.function.Function;

//Function 接口接受一个参数并生成结果。默认方法可用于将多个函数链接在一起（compose, andThen）
@FunctionalInterface
public interface FunctionSource<T, R> {
  //将Function对象应用到输入的参数上，然后返回计算结果。
  R apply(T t);
  //将两个Function整合，并返回一个能够执行两个Function对象功能的Function对象。
  default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
    Objects.requireNonNull(before);
    return (V v) -> apply(before.apply(v));
  }
  //一个执行链
  default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
    Objects.requireNonNull(after);
    return (T t) -> after.apply(apply(t));
  }

  static <T> Function<T, T> identity() {
    return t -> t;
  }
}