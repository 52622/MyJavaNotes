import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFileExample {
    public static void copyFile(String src,String dest) throws IOException {
        FileInputStream inFile = new FileInputStream(src);
        FileOutputStream outFile = new FileOutputStream(dest);
        byte[] buf = new byte[10 * 1024];
        int cnt;

        // -1 eof
        while ((cnt = inFile.read(buf,0,buf.length)) != -1) {
            outFile.write(buf,0,cnt);
        }
        inFile.close();
        outFile.close();
    }
}
