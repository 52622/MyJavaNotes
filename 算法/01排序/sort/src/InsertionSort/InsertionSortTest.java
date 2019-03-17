package InsertionSort;

public class InsertionSortTest {
    public static void main(String[] args) {
        int arr[] = {1,3,2,4,6,5};
        for (int i = 1; i < arr.length; i++) {
            int toBeInsert = arr[i];
            int j = i;
            while (j>0 && toBeInsert < arr[j-1]) {
                arr[j] = arr[j-1];
                j--;
            }

            if(j!=i)
                arr[j]=toBeInsert;
        }
        for (int a : arr
        ) {
            System.out.println(a + ",");
        }
    }
}
