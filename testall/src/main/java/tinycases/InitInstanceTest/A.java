package tinycases.InitInstanceTest;

//http://jm.taobao.org/2010/07/21/331/
public class A extends B {
  public int a = 10;
  public A() {
    super();
    System.out.println(a);
    a = 200;
  }

  public static void main(String[] args) {
    System.out.println(new A().a);
  }
  /**
   1，为A类分配内存空间，初始化所有成员变量为默认值，包括primitive类型(int=0,boolean=false,…)和Reference类型。
   2，调用A类构造函数。
   3，调用B类构造函数。
   4，调用Object空构造函数。（java编译器会默认加此构造函数，且object构造函数是个空函数，所以立即返回）
   5，初始化B类成员变量，因为B类没有成员变量，跳过。 -- 问题点
   6，执行sysout输出子类A的成员变量小a。// 此时为0
   7，初始化A类成员变量，将A类成员变量小a赋值100。 -- 问题点
   8，执行sysout输出当前A类的成员变量小a。// 此时为100
   9，赋值当前A类的成员变量小a为200。
   10，main函数中执行sysout，输出A类实例的成员变量小a。// 此时为200
   */
}
