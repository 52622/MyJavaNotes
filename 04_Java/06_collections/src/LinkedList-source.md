底层数据结构是双向链表



主要的方法

```shell
增加一个元素
add(E e) -> linkLast(E e)
1. last节点是否为null，是的话就说明原来的链表没有元素，直接把节点赋值成first
2. 如果不是，把last节点的下一个节点指向新插入的node

查询
get(int index) -> node(int index)
1. 二分法查找
2. index < (size >> 1)，距离链表头比较近，从头部开始遍历
3. 从后往前遍历

```



特点

查找慢

删除增加快

