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
public static int[] sort(int[] sourceArray) throws Exception {
    
}
```



