package QuickSort;

import java.util.Arrays;

//快排
//在冒泡排序基础上的递归分治
//https://www.cnblogs.com/MOBIN/p/4681369.html
public class QuickSortTest {
  public static void main(String[] args) {
    int arr[] = {5,6,7,8,4,3,2,1};
    int[] result = quickSort(arr, 0, arr.length - 1);
    System.out.println(Arrays.toString(result));
  }

  private static int[] quickSort(int[] arr, int left, int right) {
    if (left < right) {
      /**
       * 0 7
       * 基准 0
       */
      int partitionIndex = partition(arr, left, right);
      quickSort(arr, left, partitionIndex - 1);
      quickSort(arr, partitionIndex + 1, right);
    }
    return arr;
  }

  private static int partition(int[] arr, int left, int right) {
    // 设定基准值（pivot）
    int pivot = left;
    int index = pivot + 1;
    for (int i = index; i <= right; i++) {
      if (arr[i] < arr[pivot]) {
        swap(arr, i, index);
        index++;
      }
    }
    swap(arr, pivot, index - 1);
    return index - 1;
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
