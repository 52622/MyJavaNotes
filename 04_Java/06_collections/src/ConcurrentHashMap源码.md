https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/Java%20%E5%AE%B9%E5%99%A8.md#hashmap

数据结构

```java
static final class HashEntry<K,V> {
    final int hash;
    final K key;
    volatile V value;
    volatile HashEntry<K,V> next;
}
```



分段锁（Segment） 

```java
static final class Segment<K,V> extends ReentrantLock implements Serializable {

    private static final long serialVersionUID = 2249069246763182397L;

    static final int MAX_SCAN_RETRIES =
        Runtime.getRuntime().availableProcessors() > 1 ? 64 : 1;

    transient volatile HashEntry<K,V>[] table;

    transient int count; //统计该 Segment 中的键值对个数

    transient int modCount;

    transient int threshold;

    final float loadFactor;
}
```

多个线程可以同时访问不同分段锁上的桶 



ConcurrentHashMap 在执行 size 操作时先尝试不加锁，如果连续两次不加锁操作得到的结果一致，那么可以认为这个结果是正确的 

JDK 1.8 使用了 CAS 操作来支持更高的并发度，在 CAS 操作失败时使用内置锁 synchronized



