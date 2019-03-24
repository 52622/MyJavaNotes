实现了List，RandomAccess接口

可以插入空数据，支持随机访问

底层是一个elementData数组

维护了一个size表示数组长度



主要的方法

```shell
增加一个元素到数组末尾
add(E e)
1. 确定是否要扩容
2. 插入值直接放到数组末尾，然后size加1

增加一个元素到数组指定位置
add(int index,E e)
1. 确定是否扩容
2. 复制数组，把原index到size-index-1位置的元素复制到index+1到size-index位置
3. 插入值到index位置

```



数组最好一开始就指定大小，避免扩容

减少在指定位置插入数据的操作



序列化

只会序列化有数据的那一份数组



参考

https://github.com/crossoverJie/JCSprout/blob/master/MD/ArrayList.md



