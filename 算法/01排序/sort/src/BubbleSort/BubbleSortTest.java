package BubbleSort;

//冒泡排序
public class BubbleSortTest {
    public static void main(String[] args) {
        int[] array = {1,3,2,4,6,5};
        boolean flag = true;//如果第一趟对比结果没有出现元素交换，说明序列已经排好了
        //从第一个开始，一次对比(0,1),(1,2),...(n-1,n)位置上的元素大小，如果前者大于后者，就交换位置
        for (int i = 0; i < array.length-1; i++) {
            //从第一个开始，需要对比n-1个数，第二个开始，需要对比n-2个数
            for (int j = 0; j < array.length-i-1; j++) {
                if (array[j]>array[j+1]) {
                    int tmp = array[j+1];
                    array[j+1]=array[j];
                    array[j]=tmp;
                    flag = false;
                }
            }
            if(flag)
                break;
        }
        for (int a : array
             ) {
            System.out.println(a + ",");
        }
    }
}
