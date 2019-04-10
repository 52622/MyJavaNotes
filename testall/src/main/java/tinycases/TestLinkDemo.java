package tinycases;

class Link {
  private Node root; //根节点，增加数据函数添加
  private int count; //统计元素个数
  private Object[] retData; //返回对象数组
  private int index = 0; //操作游标
  // 定义Node内部类，表示Node只为Link类服务
  private class Node { //负责 保存数据，节点关系配置
    private Object data;
    private Node next;
    public Node(Object data) {
      this.data = data;
    }
    //增加数据
    public void addNode(Node newNode) {
      if (this.next == null) {
        this.next = newNode;
      } else {
        this.next.addNode(newNode);
      }
    }
    //转换成对象数组
    public void toArrayNode() {
      Link.this.retData[Link.this.index ++] = this.data;//先把root节点的数据取出，然后游标加一
      if (this.next != null){ //如果下个节点还有数据，则递归获取
        this.next.toArrayNode();
      }
    }
    //查找数据
    public boolean containsNode(Object search) {
      if (search.equals(this.data)) {
        return true; //找到数据
      } else {
        if (this.next != null) { //还有后续节点
          return this.next.containsNode(search);//递归查找
        } else {
          return false;
        }
      }
    }
    //根据索引取得数据
    public Object getNode(int searchIndex) {
      if (Link.this.index++ == searchIndex) {
        return this.data;
      } else {
        return this.next.getNode(searchIndex);
      }
    }
    //修改指定索引的数据
    public void setNode(int searchIndex, Object newData) {
      if (Link.this.index ++ == searchIndex) {
        this.data = newData;
      } else {
        if (this.next != null) {
          this.next.setNode(searchIndex,newData);
        }
      }
    }
    //删除元素
    public void removeDataNode(Node previous, Object data) {
      if (this.data.equals(data)) {
        previous.next = this.next; //中间是this，如果this的data是所要删除的data，那么把this之前的一个node指向this之后的一个node
      } else {
        this.next.removeDataNode(this, data);
      }
    }
  }
  // ---------以下是Link类定义-----------
  //增加元素
  public void add(Object data) {
    if ( data == null ) {//不允许存放空值数据
      return;
    }
    Node newNode = new Node(data); //创建一个新的节点
    if (this.root == null) {
      this.root = newNode;
    } else {
      this.root.addNode(newNode);
    }
    this.count ++;
  }
  //获取链表大小
  public int size() {
    return this.count;
  }
  //判断链表是否为空
  public boolean isEmpty() {
    if (this.root == null && this.count == 0 ) {
      return false;
    } else {
      return true;
    }
  }
  //链表转换成对象数组
  public Object[] toArray() {
    if (this.count == 0) { //如果链表没有数据，那么就返回null
      return null;
    }
    this.retData = new Object[this.count];//如果count不为零，那么开辟指定空间的对象数组
    this.index = 0;//游标初始化为0
    this.root.toArrayNode();//交给Node类进行数据的取出
    return this.retData;//返回对象数组
  }
  //查找数据
  public boolean contains(Object search) {
    if (search == null || this.root == null) {
      return false;
    }
    return this.root.containsNode(search);
  }
  //根据索引取得数据
  public Object get(int searchIndex) {
    if (searchIndex >= this.count) {
      return null;
    }
    this.index = 0;
    return this.root.getNode(searchIndex);
  }
  //修改指定索引的数据
  public void setData(int searchIndex, Object newData) {
    if (searchIndex >= this.count) {
      return ;
    } else {
      this.index = 0;
      this.root.setNode(searchIndex, newData);
    }
  }
  //删除数据
  public void removeData(Object data) {
    if (this.contains(data)) {
      if (this.root.data.equals(data)) {
        this.root = this.root.next;
      } else {
        this.root.next.removeDataNode(this.root, data);
      }
      this.count --;
    }
  }

}

class Factory {
  public static Link getInstance() {
    return new Link();
  }
}

public class TestLinkDemo {
  public static void main(String[] args) {
    Link all = Factory.getInstance();
    //
    all.add("AAA");
    all.add("BBB");
    all.add("CCC");
    //
    System.out.println("链表大小为： " + all.size());
    //
    Object[] result = all.toArray();
    System.out.println("链表转换成对象数组并输出: ");
    for ( Object x : result ) {
      System.out.println(x);
    }
    //查询数据方法
    System.out.println("查询数据方法: ");
    System.out.println(all.contains("AAA"));
    System.out.println(all.contains("D"));
    //取得索引数据
    System.out.println("查找索引为0的数据: ");
    System.out.println(all.get(0));
    System.out.println("查找索引为1的数据: ");
    System.out.println(all.get(1));
    //修改索引数据
    System.out.println("修改索引数据: ");
    all.setData(0,"DDD");
    Object[] result1 = all.toArray();
    System.out.println("修改索引数据并输出: ");
    for ( Object x : result1 ) {
      System.out.println(x);
    }
    //删除数据
    System.out.println("删除数据: ");
    all.removeData("BBB");
    Object[] result2 = all.toArray();
    System.out.println("删除数据并输出: ");
    for ( Object x : result2 ) {
      System.out.println(x);
    }
  }
}

