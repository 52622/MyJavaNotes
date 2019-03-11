import java.io.File;

public class TestFile {
    public static void listFiles(File dir) {
        if(null == dir || !dir.exists()) {
            return;
        }
        if(dir.isFile()){
            System.out.println(dir.getName());
            return;
        }
        for (File file : dir.listFiles()
             ) {
            listFiles(file);
        }
    }

    public static void main(String[] args) {
        listFiles(new File("/Users/joy/copywang/IdeaProjects/MyJavaNotes/04_Java/05_io/fileTest"));
    }
    //jdk1.7 Paths Files
}
