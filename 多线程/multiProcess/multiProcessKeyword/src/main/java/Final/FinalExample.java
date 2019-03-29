package Final;

public class FinalExample {
  //类变量（static修饰的变量） - 1
  //实例变量 - 2

  private final int a = 1;//2.1 声明变量的时候给实例变量赋初值
  private final String str;
  private final static boolean b;
  private final static int d = 1;//1.1 声明变量的时候给类变量赋初值
  private final double c;

//  private final char ch; // 没有在2.1 2.2 2.3位置赋值，报错

  {
    str = "非静态初始化代码块赋值";//2.2 非静态初始化代码块赋值
  }

  static {
    b = true;//1.2 静态代码块中给类变量赋初值
//    str = "非静态变量不能再静态初始块中赋值";
  }

  public FinalExample() {
    c = 1.0;//2.3 构造器中赋初值
//    a = 10;//已经赋值了不能再更改
  }

  public void a() {
//    ch = 'a';//实例方法不能给final实例变量赋值
  }

  public void test(final int a) {
    final int b;
    b = 1;
//    b = 2;//上面已经赋值，不能重复赋值
//    a = 3;//入参已经赋值，不能重复赋值
  }
}
