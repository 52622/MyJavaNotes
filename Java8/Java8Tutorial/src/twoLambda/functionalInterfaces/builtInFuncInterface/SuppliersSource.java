package twoLambda.functionalInterfaces.builtInFuncInterface;

@FunctionalInterface
//Supplier 接口产生给定泛型类型的结果 相当于生产者
public interface SuppliersSource<T> {
  /**
   * Gets a result.
   *
   * @return a result
   */
  T get();
}