package bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class BioClient {
  public static void main(String[] args) throws Exception {
    // 创建10个线程，模拟多个客户端连接服务端
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        try {
          run();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }).start();
      Thread.sleep(1000);
    }
  }

  private static void run() throws IOException, InterruptedException {
    Socket server = null;
    server = new Socket("127.0.0.1", 3333);
    server.getOutputStream().write((new Date() + " client").getBytes());
  }
}
