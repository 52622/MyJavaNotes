package oneBasic;

public class DefaultMethodTest {
  public static void main(String[] args) {
    //匿名内部类方式访问接口的default方法
    //一个内部类实现了接口里的抽象方法并且返回一个内部类对象，之后我们让接口的引用来指向这个对象
    Formula formula = new Formula() {
      @Override
      public double calculate(int a) {
        return sqrt(a*100);
      }
    };
    System.out.println(formula.calculate(100));
    System.out.println(formula.sqrt(16));
    //子类也可以重写父接口的default方法
    SubFormula subFormula = new SubFormula();
    System.out.println(subFormula.calculate(0));
    System.out.println(subFormula.sqrt(0));
  }
}
