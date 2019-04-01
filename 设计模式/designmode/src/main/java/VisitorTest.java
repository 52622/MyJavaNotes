import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: VisitorTest
 * Author:   copywang
 * Date:     2018/11/14 14:06
 * Description: 访问者模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class VisitorTest {
  // todo 暂时没清楚具体有什么用
  // 在数据基础类里面有一个方法接受访问者，将自身引用传入访问者。
  // 电脑部件:一个接收访问者的方法 accept(visitor) 元素类，元素的执行算法可以随着访问者改变而改变
    // 鼠标： accept(visitor) -> visitor.visit(this)
    // 键盘： accept(visitor) -> visitor.visit(this)
    // 显示器： accept(visitor) -> visitor.visit(this)
    // 电脑实体：一个包含了上面3个部件的数组，遍历执行 accept(visitor) -> visitor.visit(visitor)
  // 访问者接口：
    // visit(电脑实体)
    // visit(显示器)
    // visit(键盘)
    // visit(鼠标)
  // 访问者实现类：
    // visit(电脑实体) -> 显示访问电脑实体
    // visit(显示器) -> 显示访问显示器
    // visit(键盘) -> 显示访问键盘
    // visit(鼠标) -> 显示访问鼠标

  public static void main(String[] args) {
    Customer customer1 = new Customer("customer1");
    customer1.addOrder(new Order("order1", "item1"));
    customer1.addOrder(new Order("order2", "item1"));
    customer1.addOrder(new Order("order3", "item1"));

    Order order = new Order("order_a");
    order.addItem(new Item("item_a1"));
    order.addItem(new Item("item_a2"));
    order.addItem(new Item("item_a3"));
    Customer customer2 = new Customer("customer2");
    customer2.addOrder(order);

    CustomerGroup customers = new CustomerGroup();
    customers.addCustomer(customer1);
    customers.addCustomer(customer2);

    GeneralReport visitor = new GeneralReport();
    customers.accept(visitor);
    visitor.displayResults();
  }


  public interface Element {
    void accept(Visitor visitor);
  }
  static class CustomerGroup {

    private List<Customer> customers = new ArrayList<>();

    void accept(Visitor visitor) {
      for (Customer customer : customers) {
        customer.accept(visitor);
      }
    }

    void addCustomer(Customer customer) {
      customers.add(customer);
    }
  }
  public static class Customer implements Element {

    private String name;
    private List<Order> orders = new ArrayList<>();

    Customer(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }

    void addOrder(Order order) {
      orders.add(order);
    }

    public void accept(Visitor visitor) {
      visitor.visit(this);
      for (Order order : orders) {
        order.accept(visitor);
      }
    }
  }
  public static class Order implements Element {

    private String name;
    private List<Item> items = new ArrayList();

    Order(String name) {
      this.name = name;
    }

    Order(String name, String itemName) {
      this.name = name;
      this.addItem(new Item(itemName));
    }

    String getName() {
      return name;
    }

    void addItem(Item item) {
      items.add(item);
    }

    public void accept(Visitor visitor) {
      visitor.visit(this);

      for (Item item : items) {
        item.accept(visitor);
      }
    }
  }
  public static class Item implements Element {

    private String name;

    Item(String name) {
      this.name = name;
    }

    String getName() {
      return name;
    }

    public void accept(Visitor visitor) {
      visitor.visit(this);
    }
  }
  public interface Visitor {
    void visit(Customer customer);

    void visit(Order order);

    void visit(Item item);
  }
  public static class GeneralReport implements Visitor {

    private int customersNo;
    private int ordersNo;
    private int itemsNo;

    public void visit(Customer customer) {
      System.out.println(customer.getName());
      customersNo++;
    }

    public void visit(Order order) {
      System.out.println(order.getName());
      ordersNo++;
    }

    public void visit(Item item) {
      System.out.println(item.getName());
      itemsNo++;
    }

    public void displayResults() {
      System.out.println("Number of customers: " + customersNo);
      System.out.println("Number of orders:    " + ordersNo);
      System.out.println("Number of items:     " + itemsNo);
    }
  }
}
