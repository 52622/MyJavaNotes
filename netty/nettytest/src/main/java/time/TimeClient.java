package time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
  public static void main(String[] args) throws Exception {
    String host = args[0];
    int port = Integer.parseInt(args[1]);
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap(); // (1) non-server channels such as a client-side or connectionless channel
      b.group(workerGroup); // (2) The boss worker is not used for the client side
      b.channel(NioSocketChannel.class); // (3) NioSocketChannel is being used to create a client-side Channel
      b.option(ChannelOption.SO_KEEPALIVE, true); // (4) the client-side SocketChannel does not have a parent
      b.handler(new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) throws Exception {
          ch.pipeline().addLast(new TimeClientHandler());
        }
      });

      // Start the client.
      ChannelFuture f = b.connect(host, port).sync(); // (5) should call the connect() method instead of the bind() method

      // Wait until the connection is closed.
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
    }
  }
}
