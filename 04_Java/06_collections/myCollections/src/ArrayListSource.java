import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ArrayListSource
 * Author:   copywang
 * Date:     2019/3/6 16:11
 * Description: ArrayList源码记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class ArrayListSource {
  // RandomAccess 支持随机访问

  // eg1 add - 扩容
  /**
  public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
  }
   */
  /**
   private void grow(int minCapacity) {
   // overflow-conscious code
   int oldCapacity = elementData.length;
   int newCapacity = oldCapacity + (oldCapacity >> 1); // 扩容为1.5倍原来的大小
   if (newCapacity - minCapacity < 0)
   newCapacity = minCapacity;
   if (newCapacity - MAX_ARRAY_SIZE > 0)
   newCapacity = hugeCapacity(minCapacity);
   // minCapacity is usually close to size, so this is a win:
   elementData = Arrays.copyOf(elementData, newCapacity);
   }
   */
  //把原数组整个复制到新数组中，操作代价很高，最好初始化数组的时候直接指定大小
  /**
   public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
  @SuppressWarnings("unchecked")
  T[] copy = ((Object)newType == (Object)Object[].class)
  ? (T[]) new Object[newLength]
  : (T[]) Array.newInstance(newType.getComponentType(), newLength);
  System.arraycopy(original, 0, copy, 0,
  Math.min(original.length, newLength));
  return copy;
  }
   */

  // eg2 remove
  // 将 index+1 后面的元素都复制到 index 位置上，该操作的时间复杂度为 O(N)，可以看出 ArrayList 删除元素的代价是非常高的
  /**
  public E remove(int index) {
    rangeCheck(index);

    modCount++;
    E oldValue = elementData(index);

    int numMoved = size - index - 1;
    if (numMoved > 0)
      System.arraycopy(elementData, index+1, elementData, index,
          numMoved);
    elementData[--size] = null; // clear to let GC do its work

    return oldValue;
  }
   // System.arraycopy是个native方法
   public static native void arraycopy(Object src,  int  srcPos,
   Object dest, int destPos,
   int length);
  */
  // eg3 Fail-Fast modCount记录结构发生变化
  // 添加或者删除至少一个元素的所有操作，或者是调整内部数组的大小，仅仅只是设置元素的值不算结构发生变化
  // 序列化或者迭代 ConcurrentModificationException
  // writeObject
  // forEach


  // eg4 序列化
  // 保存元素的数组不一定都会被使用，那么就没必要全部进行序列化 保存元素的数组 elementData 使用 transient 修饰
  // 序列化时需要使用 ObjectOutputStream 的 writeObject() 将对象转换为字节流并输出
  // 反序列化使用的是 ObjectInputStream 的 readObject() 方法
  /**
  ArrayList list = new ArrayList();
  ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
  oos.writeObject(list);
   */

  // eg5 线程安全的List
  /**
   * List<String> list = new ArrayList<>();
   * List<String> synList = Collections.synchronizedList(list);
   */
}
