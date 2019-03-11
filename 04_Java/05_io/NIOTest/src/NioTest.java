import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {
    public static void main(String[] args) {

    }
    public static void nioCopy(String src,String dest) throws Exception {
        FileInputStream in = new FileInputStream(src);//file input byte stream
        FileChannel inChannel = in.getChannel();//get input channel
        FileOutputStream out = new FileOutputStream(dest);
        FileChannel outChannel = out.getChannel();
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        while (true) {
            int r = inChannel.read(buf);
            if (r == -1) {
                break;
            }
            buf.flip();//to write
            outChannel.write(buf);
            buf.clear();
        }
    }
}
