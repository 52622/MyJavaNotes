package CountSort;

import java.util.Arrays;

//计数排序
public class CountSortTest {
  public static void main(String[] args) {
    int arr[] = {5,6,7,8,4,3,2,1,1,2,3};
    int maxValue = getMaxValue(arr);

    countingSort(arr, maxValue);
    System.out.println(Arrays.toString(arr));
  }

  private static int[] countingSort(int[] arr, int maxValue) {
    int bucketLen = maxValue + 1;
    int[] bucket = new int[bucketLen];

    for (int value : arr) {
      bucket[value]++;
    }

    int sortedIndex = 0;
    for (int j = 0; j < bucketLen; j++) {
      while (bucket[j] > 0) {
        arr[sortedIndex++] = j;
        bucket[j]--;
      }
    }
    return arr;
  }

  private static int getMaxValue(int[] arr) {
    int maxValue = arr[0];
    for (int value : arr) {
      if (maxValue < value) {
        maxValue = value;
      }
    }
    return maxValue;
  }
}
