package InsertionSort;

//插入排序
public class InsertionSortTest {
    public static void main(String[] args) {
        int arr[] = {1,3,2,4,6,5};
        //第一位默认为已排序好的
        //从第二位开始，依次和已经排序好的序列进行对比，找到合适的位置并插入
        /**
         * 1,3,2,4,5,6
         * 1. round: i=1,toBeInsert=3,j=1,j=1>0 and 3>1; nothing change {1,3,2,4,6,5}
         * 2. round: i=2,toBeInsert=2,j=2,j=2>0 and 2<3; -> arr[2]=3 {1,3,3,4,6,5} -> j--=1, j=1>0 and 2>1 nothing change -> j=1!=2 -> arr[1]=2 {1,2,3,4,6,5}
         * ...
         */
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
