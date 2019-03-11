import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class NetTest {
    //InetAddress：用于表示网络上的硬件资源，即 IP 地址；
    //URL：统一资源定位符；
    //Sockets：使用 TCP 协议实现网络通信；
    //Datagram：使用 UDP 协议实现网络通信。
    public static void main(String[] args) throws Exception {
        //静态方法创建实例
        //Inet4Address.getByName("http://www.baidu.com");

        //url直接读取字节流
        URL url = new URL("https://www.baidu.com");
        InputStream in = url.openStream();//byte
        InputStreamReader reader = new InputStreamReader(in, "utf-8");//char
        BufferedReader buf = new BufferedReader(reader);//for cache
        String line;
        while ((line = buf.readLine())!=null)  {
            System.out.println(line);
        }
        buf.close();//会自动调用in.close()

        //ServerSocket：服务器端类
        //Socket：客户端类
        //服务器和客户端通过 InputStream 和 OutputStream 进行输入输出
        //ServerSocket(port,timeout)
        //accept()
        //Server InputStream -> Client OutputStream
        //close()

        //Socket(host,port)
        //Client InputStream -> Server OutputStream
        //close()

        //Datagram
        //DatagramSocket: 通信类
        //DatagramPacket: 数据包类

    }
}
