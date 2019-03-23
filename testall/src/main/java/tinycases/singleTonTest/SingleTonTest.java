package tinycases.singleTonTest;

//http://www.cnblogs.com/javaee6/p/3714716.html?utm_source=tuicool&utm_medium=referral
public class SingleTonTest {
    public static void main(String[] args) {
        SingleTon s = SingleTon.getInstance();
        System.out.println(s.c1);//1
        System.out.println(s.c2);//0
    }
}
