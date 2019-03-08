https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/Java%20%E5%AE%B9%E5%99%A8.md#hashmap



WeakReference 关联的对象在下一次垃圾回收时会被回收 

```java
private static class Entry<K,V> extends WeakReference<Object> implements Map.Entry<K,V>
```



