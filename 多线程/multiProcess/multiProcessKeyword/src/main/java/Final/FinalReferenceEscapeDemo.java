package Final;

public class FinalReferenceEscapeDemo {
  private final int a;
  private FinalReferenceEscapeDemo referenceDemo;

  public FinalReferenceEscapeDemo() {
    a = 1;  //1
    referenceDemo = this; //2 引用对象“this”逸出，该代码依然存在线程安全的问题
  }

  public void writer() {
    new FinalReferenceEscapeDemo();
  }

  public void reader() {
    if (referenceDemo != null) {  //3
      int temp = referenceDemo.a; //4
    }
  }
}

