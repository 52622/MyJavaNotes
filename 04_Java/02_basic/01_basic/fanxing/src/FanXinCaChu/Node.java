/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Node
 * Author:   copywang
 * Date:     2019/3/5 17:08
 * Description: 泛型擦除
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package FanXinCaChu;

//public class Node<T> { 运行时T会擦除成Object
public class Node<T extends Comparable<T>> { //重新设置Bounds 运行时T会变成Comparable
  private T data;
  private Node<T> next;

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Node<T> getNext() {
    return next;
  }

  public void setNext(Node<T> next) {
    this.next = next;
  }

  public Node(T data, Node<T> next) {
    this.data = data;
    this.next = next;
  }
}
