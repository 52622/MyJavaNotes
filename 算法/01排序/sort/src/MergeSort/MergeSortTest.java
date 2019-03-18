package MergeSort;

import java.util.Arrays;

//https://www.cnblogs.com/chengxiao/p/6194356.html
public class MergeSortTest {
  public static void main(String[] args) throws Exception {
    int arr[] = {5,6,7,8,4,3,2,1};
    int[] merge = sort(arr);
    System.out.println(Arrays.toString(merge));
  }

  public static int[] sort(int[] sourceArray) throws Exception {
    // 对 arr 进行拷贝，不改变参数内容
    int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

    //只有一个元素
    if (arr.length < 2) {
      return arr;
    }

    //原数组拆成2个
    int middle = (int) Math.floor(arr.length / 2);

    int[] left = Arrays.copyOfRange(arr, 0, middle);
    int[] right = Arrays.copyOfRange(arr, middle, arr.length);

    //拆到每个序列只有1个元素，递归
    return merge(sort(left), sort(right));
    //merge(sort([5,6,7,8]),sort([4,3,2,1]))
    //merge(merge(sort([5,6]),sort([7,8])),merge(sort([4,3]),sort([2,1])))
    //merge(merge(merge([5],[6]),merge([7],[8])),merge(merge([4],[3]),merge([2],[1])))
  }

  /**
   * merge([5],[6]) -> result[2]
   * left[0]=5 <= right[0]=6
   *
   */
  public static int[] merge(int[] left, int[] right) {
    int[] result = new int[left.length + right.length];
    int i = 0;
    while (left.length > 0 && right.length > 0) {
      if (left[0] <= right[0]) {
        result[i++] = left[0];
        left = Arrays.copyOfRange(left, 1, left.length);
      } else {
        result[i++] = right[0];
        right = Arrays.copyOfRange(right, 1, right.length);
      }
    }

    while (left.length > 0) {
      result[i++] = left[0];
      left = Arrays.copyOfRange(left, 1, left.length);
    }

    while (right.length > 0) {
      result[i++] = right[0];
      right = Arrays.copyOfRange(right, 1, right.length);
    }

    return result;
  }
}
