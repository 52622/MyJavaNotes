public class CopyOnWriteArrayListSource {
    // 写操作在一个复制的数组上进行，读操作还是在原始数组中进行
    // 写操作加锁
    /**
     *
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);// 写完之后把读数组指向写好的新数组
            return true;
        } finally {
            lock.unlock();
        }
    }
     */
    /**
     *
     * 占用内存大
     * 数据有延时，不一致，适合读多写少的场景
     */
}
