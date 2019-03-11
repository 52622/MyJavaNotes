public class SelectorTest {

    //创建选择器
    //selector=Selector.open()

    //注册一个通道到选择器上
    //ssl=ServerSocketChannel.open()
    //ssl.configureBlocking(false) //必须是非阻塞的
    //ssl.register(selector,SelectionKey.OP_ACCEPT)
    //SelectionKey.OP_CONNECT
    //SelectionKey.OP_ACCEPT
    //SelectionKey.OP_READ
    //SelectionKey.OP_WRITE

    //监听事件
    //num=selector.select()

    //获取到达的事件
    /**
     * Set<SelectionKey> keys = selector.selectedKeys();
     * Iterator<SelectionKey> keyIterator = keys.iterator();
     * while (keyIterator.hasNext()) {
     *     SelectionKey key = keyIterator.next();
     *     if (key.isAcceptable()) {
     *         // ...
     *     } else if (key.isReadable()) {
     *         // ...
     *     }
     *     keyIterator.remove();
     * }
     */

    //事件循环
    /**
     * while (true) {
     *     int num = selector.select();
     *     Set<SelectionKey> keys = selector.selectedKeys();
     *     Iterator<SelectionKey> keyIterator = keys.iterator();
     *     while (keyIterator.hasNext()) {
     *         SelectionKey key = keyIterator.next();
     *         if (key.isAcceptable()) {
     *             // ...
     *         } else if (key.isReadable()) {
     *             // ...
     *         }
     *         keyIterator.remove();
     *     }
     * }
     */
}
