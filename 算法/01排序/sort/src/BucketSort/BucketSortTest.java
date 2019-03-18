package BucketSort;

import InsertionSort.InsertionSortTest;

import java.util.Arrays;

//桶排序
public class BucketSortTest {
  public static void main(String[] args) throws Exception {
    int arr[] = {5,6,7,8,4,3,2,1,1,2,3};
    System.out.println(Arrays.toString(sort(arr)));
  }

  private static final InsertionSortTest insertSort = new InsertionSortTest();

  public static int[] sort(int[] sourceArray) throws Exception {
    // 对 arr 进行拷贝，不改变参数内容
    int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
    return bucketSort(arr, 5);
  }

  private static int[] bucketSort(int[] arr, int bucketSize) throws Exception {
    if (arr.length == 0) {
      return arr;
    }

    int minValue = arr[0];
    int maxValue = arr[0];
    for (int value : arr) {
      if (value < minValue) {
        minValue = value;
      } else if (value > maxValue) {
        maxValue = value;
      }
    }

    int bucketCount = (int) Math.floor((maxValue - minValue) / bucketSize) + 1;
    int[][] buckets = new int[bucketCount][0];

    // 利用映射函数将数据分配到各个桶中
    for (int i = 0; i < arr.length; i++) {
      int index = (int) Math.floor((arr[i] - minValue) / bucketSize);
      buckets[index] = arrAppend(buckets[index], arr[i]);
    }

    int arrIndex = 0;
    for (int[] bucket : buckets) {
      if (bucket.length <= 0) {
        continue;
      }
      // 对每个桶进行排序，这里使用了插入排序
      bucket = insertSort.sort(bucket);
      for (int value : bucket) {
        arr[arrIndex++] = value;
      }
    }

    return arr;
  }

  /**
   * 自动扩容，并保存数据
   *
   * @param arr
   * @param value
   */
  private static int[] arrAppend(int[] arr, int value) {
    arr = Arrays.copyOf(arr, arr.length + 1);
    arr[arr.length - 1] = value;
    return arr;
  }
}
