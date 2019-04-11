package lambdasinaction.chap3;

import java.io.*;
public class ExecuteAround {

	public static void main(String ...args) throws IOException{

        // method we want to refactor to make more flexible
        String result = processFileLimited();
        System.out.println(result);

        System.out.println("---");

		// 普通方法的扩展，函数式接口的方法，可以有多个实现，不需要修改实现类
		String oneLine = processFile((BufferedReader b) -> b.readLine());
		System.out.println(oneLine);

		String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
		System.out.println(twoLines);

	}

    public static String processFileLimited() throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("src/main/resources/lambdasinaction/chap3/data.txt"))) {
            return br.readLine();
        }
    }


	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/lambdasinaction/chap3/data.txt"))){
			return p.process(br);
		}

	}

	public interface BufferedReaderProcessor{
		public String process(BufferedReader b) throws IOException;

	}
}
