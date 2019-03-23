package tinycases.singleTonTest;

public class SingleTon {
    private static SingleTon s = new SingleTon();//注意这里的初始化顺序
    public static int c1;
    public static int c2 = 0;

    private SingleTon() {
        c1++;
        c2++;
    }

    public static SingleTon getInstance() {
        return s;
    }
}
