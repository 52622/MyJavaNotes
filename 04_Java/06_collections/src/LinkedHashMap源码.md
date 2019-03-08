https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/Java%20%E5%AE%B9%E5%99%A8.md#hashmap



```java
public class LinkedHashMap<K,V> extends HashMap<K,V> implements Map<K,V>
```

双向链表

维护插入顺序或者 LRU 顺序

```java
/**
 * The head (eldest) of the doubly linked list.
 */
transient LinkedHashMap.Entry<K,V> head;

/**
 * The tail (youngest) of the doubly linked list.
 */
transient LinkedHashMap.Entry<K,V> tail;
```

维护顺序的函数 

```java
void afterNodeAccess(Node<K,V> p) { }
void afterNodeInsertion(boolean evict) { }
```

accessOrder 决定了顺序，默认为 false

false：维护的是插入顺序 

true：维护访问顺序，当一个节点被访问时 ，将该节点移到链表尾部 

```java
void afterNodeAccess(Node<K,V> e) { // move node to last
    LinkedHashMap.Entry<K,V> last;
    if (accessOrder && (last = tail) != e) {
        LinkedHashMap.Entry<K,V> p =
            (LinkedHashMap.Entry<K,V>)e, b = p.before, a = p.after;
        p.after = null;
        if (b == null)
            head = a;
        else
            b.after = a;
        if (a != null)
            a.before = b;
        else
            last = b;
        if (last == null)
            head = p;
        else {
            p.before = last;
            last.after = p;
        }
        tail = p;
        ++modCount;
    }
}
```



```java
void afterNodeInsertion(boolean evict) { // possibly remove eldest evict构面map的时候false，此时为true
    LinkedHashMap.Entry<K,V> first;
    if (evict && (first = head) != null && removeEldestEntry(first)) {
        K key = first.key;
        removeNode(hash(key), key, null, false, true);
    }
}
```



```java
protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
    return false;// 默认false为不移除
}
```



LRU

```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final int MAX_ENTRIES = 3;

    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;//最大缓存空间为3
    }

    LRUCache() {
        super(MAX_ENTRIES, 0.75f, true);
    }
}
```



