package SelectionSort;

//选择排序
public class SelectionSortTest {
    public static void main(String[] args) {
        int[] array = {1,3,2,4,6,5};
        //拆成2部分，从第一位开始，从第二位开始到第n位如果有小于第一位的，交换两个数的位置
        //第二次，第一位是最小数，第二位开始，从第三位开始到第n位如果有小于第二位的，交换两个数的位置
        for (int i = 0; i < array.length-1; i++) {
            int minIndex = i;
            for (int j = i+1; j < array.length; j++) {
                if(array[j]<array[i])
                    minIndex=j;
            }
            if(i!=minIndex) {
                int tmp = array[i];
                array[i]=array[minIndex];
                array[minIndex]=tmp;
            }
        }
        for (int a:array
             ) {
            System.out.println(a+",");
        }
    }
}
