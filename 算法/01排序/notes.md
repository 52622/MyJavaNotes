![åå¤§ç"å¸æåºç®æ³ æ¦è§æªå¾](assets/sort.png)



## 冒泡排序

步骤

1. 比较相邻元素，第一个比第二个大，交换位置
2. 每一对都这样对比，第一轮之后，最大的数放在最后
3. 重复执行，到倒数第二个
4. 继续重复执行，到倒数第三个
5. 一直到没有任何一对需要对比的数字，结束循环

最快的时候：

已经排好序

最慢的时候：

刚好是倒序



Python

```python
def bubbleSort(arr):
    for i in range(1,len(arr)):
        for j in range(0,len(arr)-i):
            if arr[j]>arr[j+1]:
                arr[j],arr[j+1] = arr[j+1],arr[j]
    return j
```



java

```java
public class BubbleSort implements IArraySort {

    @Override
    public int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        for (int i = 1; i < arr.length; i++) {
            // 设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;

            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;

                    flag = false;
                }
            }

            if (flag) {
                break;
            }
        }
        return arr;
    }
}
```



## 选择排序

步骤

1. 未排序的序列中找到最大的数，放到排序起始位置
2. 剩余未排序的，找到最大的，放到排序的末尾
3. 重复2

```python
def selectionSort(arr):
    for i in range(len(arr)-1):
        minIndex = i
        for j in range(i+1,len(arr)):
            if arr[j]<arr[minIndex]:
                minIndex = j
            if i != minIndex:
                arr[i],arr[minIndex] = arr[minIndex],arr[i]
                
    return arr
```

```java
public class SelectionSort implements IArraySort {

    @Override
    public int[] sort(int[] sourceArray) throws Exception {
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        // 总共要经过 N-1 轮比较
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;

            // 每轮需要比较的次数 N-i
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    // 记录目前能找到的最小值元素的下标
                    min = j;
                }
            }

            // 将找到的最小值和i位置所在的值进行交换
            if (i != min) {
                int tmp = arr[i];
                arr[i] = arr[min];
                arr[min] = tmp;
            }

        }
        return arr;
    }
}
```



## 插入排序

步骤

1. 将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列。
2. 从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置。（如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面。）



```python
def insertionSort(arr):
    for i in range(len(arr)):
        preIndex=i-1
        cur=arr[i]
        while preIndex >=0 and arr[preIndex] > cur:
            arr[preIndex+1]=arr[preIndex]
            preIndex-=1
        arr[preIndex+1]=cur
    return arr
```



```java
public class InsertSort implements IArraySort {

    @Override
    public int[] sort(int[] sourceArray) throws Exception {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for (int i = 1; i < arr.length; i++) {

            // 记录要插入的数据
            int tmp = arr[i];

            // 从已经排序的序列最右边的开始比较，找到比其小的数
            int j = i;
            while (j > 0 && tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            // 存在比其小的数，插入
            if (j != i) {
                arr[j] = tmp;
            }

        }
        return arr;
    }
}
```

