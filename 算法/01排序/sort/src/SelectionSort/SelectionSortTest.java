package SelectionSort;

public class SelectionSortTest {
    public static void main(String[] args) {
        int[] array = {1,3,2,4,6,5};
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
