import java.io.*;

public class ObjectStreamTest {
    //对象操作 序列化 反序列化
    //序列化：ObjectOutputStream.writeObject()
    //反序列化：ObjectInputStream.readObject()
    static class A implements Serializable {
        private int a;
        private String b;
        A(int a,String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "a = " + a + " b= " + b;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        A a1 = new A(123, "abc");
        String objectFile = "file/a1";

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(objectFile));
        objectOutputStream.writeObject(a1);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(objectFile));
        A a2 = (A) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(a2);
    }
}
