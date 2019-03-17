package BubbleSort;

public class BubbleSortTest {
    public static void main(String[] args) {
        int[] array = {1,3,2,4,6,5};
        boolean flag = true;
        for (int i = 0; i < array.length-1; i++) {
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
