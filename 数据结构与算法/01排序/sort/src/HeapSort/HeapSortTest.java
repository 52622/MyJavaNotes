package HeapSort;

import java.util.Arrays;

//堆排序
//子结点的键值或索引总是小于（或者大于）它的父节点
//todo 没看明白转换
public class HeapSortTest {
  public static void main(String[] args) {
    int arr[] = {5,6,7,8,4,3,2,1};
    int len = arr.length;

    buildMaxHeap(arr, len);

    for (int i = len - 1; i > 0; i--) {
      swap(arr, 0, i);
      len--;
      heapify(arr, 0, len);
    }
    System.out.println(Arrays.toString(arr));
  }

  private static void buildMaxHeap(int[] arr, int len) {
    for (int i = (int) Math.floor(len / 2); i >= 0; i--) {
      heapify(arr, i, len);
    }
  }

  private static void heapify(int[] arr, int i, int len) {
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    int largest = i;

    if (left < len && arr[left] > arr[largest]) {
      largest = left;
    }

    if (right < len && arr[right] > arr[largest]) {
      largest = right;
    }

    if (largest != i) {
      swap(arr, i, largest);
      heapify(arr, largest, len);
    }
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
