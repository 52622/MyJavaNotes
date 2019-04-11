package lambdasinaction.chap8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class FactoryMain {

  public static void main(String[] args) {
    // 普通简单工厂
    Product p1 = ProductFactory.createProduct("loan");
    // lambda，方法推导，得到一个提供者的函数式接口
    Supplier<Product> loanSupplier = Loan::new;
    Product p2 = loanSupplier.get();

    //与上面同理
    Product p3 = ProductFactory.createProductLambda("loan");

  }

  static private class ProductFactory {
    public static Product createProduct(String name) {
      switch (name) {
        case "loan":
          return new Loan();
        case "stock":
          return new Stock();
        case "bond":
          return new Bond();
        default:
          throw new RuntimeException("No such product " + name);
      }
    }

    public static Product createProductLambda(String name) {
      Supplier<Product> p = map.get(name);
      if (p != null) return p.get();
      throw new RuntimeException("No such product " + name);
    }
  }

  static private interface Product {
  }

  static private class Loan implements Product {
  }

  static private class Stock implements Product {
  }

  static private class Bond implements Product {
  }

  final static private Map<String, Supplier<Product>> map = new HashMap<>();

  static {
    map.put("loan", Loan::new);
    map.put("stock", Stock::new);
    map.put("bond", Bond::new);
  }
}
