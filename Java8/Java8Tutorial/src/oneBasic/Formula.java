package oneBasic;

public interface Formula {
  double calculate(int a);

  //接口可以用默认方法实现，所以实现了此接口的子类都可以直接调用，不需要重写
  default double sqrt(int a) {
    return Math.sqrt(a);
  }
}