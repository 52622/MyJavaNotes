package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;

import java.util.Optional;

import static lambdasinaction.chap4.Dish.menu;

public class Finding {

  //带条件查找
  //anyMatch存在一个就返回true，否则返回false
  //noneMatch不存在则返回true，否则返回false
  //allMatch必须都匹配条件才返回true
  //findAny返回一个Optional包装的对象，可以为null，但是会抛出空指针异常
  //ifPresent如果存在就执行操作
  public static void main(String... args) {
    if (isVegetarianFriendlyMenu()) {
      System.out.println("Vegetarian friendly");
    }

    System.out.println(isHealthyMenu());
    System.out.println(isHealthyMenu2());

    Optional<Dish> dish = findVegetarianDish();
    dish.ifPresent(d -> System.out.println(d.getName()));
  }

  private static boolean isVegetarianFriendlyMenu() {
    return menu.stream().anyMatch(Dish::isVegetarian);
  }

  private static boolean isHealthyMenu() {
    return menu.stream().allMatch(d -> d.getCalories() < 1000);
  }

  private static boolean isHealthyMenu2() {
    return menu.stream().noneMatch(d -> d.getCalories() >= 1000);
  }

  private static Optional<Dish> findVegetarianDish() {
    return menu.stream().filter(Dish::isVegetarian).findAny();
  }

}
