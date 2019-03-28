package discard;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // ChannelInboundHandler的一个实现类
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    // Discard the received data silently.
    //((ByteBuf) msg).release(); // (3)


    //Looking into the Received Data
//    ByteBuf in = (ByteBuf) msg;
//    try {
//      while (in.isReadable()) { // (1)
//        System.out.print((char) in.readByte());
//        System.out.flush();
//      }
//    } finally {
//      ReferenceCountUtil.release(msg); // (2)
//    }

    //Writing an Echo Server
    ctx.write(msg); // (1)
    ctx.flush(); // (2)

    //Writing a Time Server


  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }
}
