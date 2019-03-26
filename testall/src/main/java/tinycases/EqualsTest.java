package tinycases;

public class EqualsTest {
  public static void main(String[] args) {
    Integer I = 1;
    Byte B = 1;
    byte b = 1;
    int i = 1;
    System.out.println(B.equals(I));//f
    System.out.println(B.equals(b));
    System.out.println(I.equals(b));//f
    System.out.println(i==b);
  }
}
