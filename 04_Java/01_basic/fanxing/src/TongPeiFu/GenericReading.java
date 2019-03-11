/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GenericReading
 * Author:   copywang
 * Date:     2019/3/5 16:34
 * Description: 通配符测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package TongPeiFu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//pecs producer extends consumer super
public class GenericReading {
  // eg1
  static List<Apple> apples = Arrays.asList(new Apple());
  static List<Fruit> fruit = Arrays.asList(new Fruit());
  static class Reader<T> {
    T readExact(List<T> list) {
      return list.get(0);
    }
  }
  static void f1() {
    Reader<Fruit> fruitReader = new Reader<Fruit>();
    // Errors: List<Fruit> cannot be applied to List<Apple>.
    // Fruit f = fruitReader.readExact(apples); // List<Fruit>与List<Apple>之间并没有任何的关系
  }

  // eg2
  static class CovariantReader<T> {
    T readCovariant(List<? extends T> list) { // 只要是满足Fruit的子类就行(包括Fruit自身)
      return list.get(0);
    }
  }
  static void f2() {
    CovariantReader<Fruit> fruitReader = new CovariantReader<Fruit>();
    Fruit f = fruitReader.readCovariant(fruit);
    Fruit a = fruitReader.readCovariant(apples);
  }

  //eg4
  static List<Apple> apples4 = new ArrayList<Apple>();
  static List<Fruit> fruit4 = new ArrayList<Fruit>();

  static <T> void writeExact(List<T> list, T item) {
    list.add(item);
  }
  static void f3() {
    // List<Fruit>与List<Apple>之间并没有任何的关系
    writeExact(apples4, new Apple());
    writeExact(fruit4, new Apple());
  }
  static <T> void writeWithWildcard(List<? super T> list, T item) {
    list.add(item);
  }
  static void f4() {
    writeWithWildcard(apples4, new Apple());
    writeWithWildcard(fruit4, new Apple());
  }

  public static void main(String[] args) {
    // eg1 <T>
    f1();
    // eg2 <? extends T>
    f2();

    // eg3
    // Wildcards allow covariance:
    List<? extends Fruit> flist = new ArrayList<Apple>();
    // Compile Error: can't add any type of object:
//     flist.add(new Apple())
    // flist.add(new Orange())
    // flist.add(new Fruit())
    // flist.add(new Object())
    flist.add(null); // Legal but uninteresting
    // We Know that it returns at least Fruit:
    Fruit f = flist.get(0);
    /**
     List<? extends Fruit> flist = new ArrayList<Fruit>();
     List<? extends Fruit> flist = new ArrayList<Apple>();
     List<? extends Fruit> flist = new ArrayList<Orange>();
     当我们尝试add一个Apple的时候，flist可能指向new ArrayList<Orange>();
     当我们尝试add一个Orange的时候，flist可能指向new ArrayList<Apple>();
     当我们尝试add一个Fruit的时候，这个Fruit可以是任何类型的Fruit，而flist可能只想某种特定类型的Fruit，编译器无法识别所以会报错。
     所以对于实现了<? extends T>的集合类只能将它视为Producer向外提供(get)元素，而不能作为Consumer来对外获取(add)元素。
     */
    // eg4 <? super T>
//    f3();
    f4();
    // eg5
    List<? super Apple> flist5 = new ArrayList<Apple>();
    flist5.add(new Apple());
    flist5.add(new Apple());
//    Fruit fruit = flist5.get(0);
    /**
     List<? super Apple> list = new ArrayList<Apple>();
     List<? super Apple> list = new ArrayList<Fruit>();
     List<? super Apple> list = new ArrayList<Object>();
     尝试通过list来get一个Apple的时候，可能会get得到一个Fruit，这个Fruit可以是Orange等其他类型的Fruit
     */
  }
}
