package tinycases.InitInstanceTest;

public class B {
  public B() {
    System.out.println(((A) this).a);
  }
}
