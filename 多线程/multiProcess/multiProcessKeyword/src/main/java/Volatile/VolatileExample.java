package Volatile;

public class VolatileExample {
  private int a = 0;
  private volatile boolean flag = false;
  public void writer(){
    a = 1;          //1
    flag = true;   //2
  }
  public void reader(){
    if(flag){      //3
      int i = a; //4
    }
  }
}
