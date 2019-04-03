package tinycases;

public class StringHashcode {

  public static void main(String[] args) {
    String a = "a";
    int i = a.hashCode();
    int j;

    System.out.println(i);
    System.out.println((j = a.hashCode()) & (j >>> 16));

    decimal2Binary(9799966);
  }

  //https://www.cnblogs.com/vsign/p/7290594.html
  public static void decimal2Binary(int n) {
    for (int i = 31; i >= 0; i--) {
      System.out.print(n >>> i & 1);
      if(i == 16)
        System.out.println();
    }
  }
}
