package lambdasinaction.chap8;


import java.util.Arrays;
import java.util.List;

public class Debugging{
    public static void main(String[] args) {
        List<Point> points = Arrays.asList(new Point(12, 2), null);// null空指针异常 使用peek输出到日志
        points.stream().map(Point::getX).forEach(System.out::println);
    }


    private static class Point{
        private int x;
        private int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }
}
