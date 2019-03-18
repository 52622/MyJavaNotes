
package ShellSort;
//希尔排序
//https://blog.csdn.net/weixin_37818081/article/details/79202115
//https://www.cnblogs.com/chengxiao/p/6104371.html
public class ShellSortTest {
  public static void main(String[] args) {
    int arr[] = {5,6,7,8,4,3,2,1};
    int gap = 1;//步进为1的时候就是一个完整序列了
    /**
     * gap=1<8/3=2.xx
     * gap=1*3+1=4>2
     * gap=4
     */
    while (gap < arr.length/3) {
      gap = gap * 3 + 1;
    }

    /**
     * 1. gap=4 先对(arr[4],arr[0]),(arr[5],arr[1]),(arr[6],arr[2]),(arr[7],arr[3])两个子序列进行插入排序
     * {4,3,2,1,5,6,7,8}
     * 2. gap=1 对整个序列插入排序
     */
    while (gap > 0) {
      for (int i = gap; i < arr.length; i++) {
        int tmp = arr[i];
        int j = i - gap;
        while (j >= 0 && arr[j] > tmp) {
          arr[j + gap] = arr[j];
          j -= gap;
        }
        arr[j + gap] = tmp;
      }
      gap = (int) Math.floor(gap / 3);
    }
    for (int a : arr
    ) {
      System.out.println(a + ",");
    }

    //希尔排序是非稳定排序算法-因为如果两个元素相等，有可能会被交换位置
    System.out.println("==================");
    int arr2[] = {5,6,7,8,4,3,2,1};
    int inc = arr2.length-1;
    do {
      inc = inc/3 + 1;
      for (int i = inc; i < arr2.length; i++) {
        if(arr2[i-inc]>arr2[i]) {
          int tmp = arr2[i];
          int j = i-inc;
          do {
            arr2[j+inc]=arr2[j];
            j-=inc;
          } while (j>0 && arr2[j]>tmp);
          arr2[j+inc] = tmp;
        }
      }
    } while (inc>1);
    for (int a : arr2
    ) {
      System.out.println(a + ",");
    }
  }
}
