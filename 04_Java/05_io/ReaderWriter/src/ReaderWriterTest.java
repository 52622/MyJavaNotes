import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReaderWriterTest {
    //InputStreamReader byte to char
    //OutputStreamWriter char to byte
    public static void readFileContent(String path) throws IOException {
        FileReader re = new FileReader(path);
        BufferedReader buf = new BufferedReader(re);

        String line;
        //逐行读取文件
        while ((line = buf.readLine())!=null) {
            System.out.println(line);
        }
        buf.close();
    }
}
