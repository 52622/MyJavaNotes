package discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
  private int port;

  public DiscardServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    //accepts an incoming connection
    EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1) multithreaded event loop that handles I/O operation
    //handles the traffic of the accepted connection once the boss accepts the connection and registers the accepted connection to the worker
    //可以在构造的时候选择用多少个线程处理请求
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap(); // (2) 封装了Channel用于启动服务
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class) // (3) NioServerSocketChannel用于接收请求连接的通道
          .childHandler(new ChannelInitializer<SocketChannel>() { // (4) 自定义扩展各种处理器，这里以自定义的处理器为例
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(new DiscardServerHandler());
            }
          })
          .option(ChannelOption.SO_BACKLOG, 128)          // (5) 通道配置，比如TCP的tcpNoDelay and keepAlive
          .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

      // Bind and start to accept incoming connections.
      ChannelFuture f = b.bind(port).sync(); // (7) 绑定端口

      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }

    new DiscardServer(port).run();
  }
}
