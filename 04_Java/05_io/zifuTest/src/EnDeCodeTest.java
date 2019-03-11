import java.io.UnsupportedEncodingException;

public class EnDeCodeTest {
    // 字符->字节 编码
    // 字节->字符 解码
    // gbk zh 2bytes en 1bytes
    // utf-8 zh 3bytes en 1bytes
    // utf-16be zh en both 2bytes -- java
    // 双字节编码的好处是可以使用一个 char 存储中文和英文
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s1 = "en";
        System.out.println(s1.getBytes().length);
        System.out.println(s1.getBytes("utf-8").length);
        String s2 = "中文";
        System.out.println(s2.getBytes().length);
        System.out.println(s2.getBytes("utf-8").length);
    }

}
