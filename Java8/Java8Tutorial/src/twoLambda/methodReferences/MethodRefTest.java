package twoLambda.methodReferences;

import twoLambda.functionalInterfaces.Converter;

public class MethodRefTest {
  public static void main(String[] args) {
    //静态方法引用
    Converter<String, Integer> converter = Integer::valueOf;
    Integer converted = converter.convert("123");
    System.out.println(converted);
    System.out.println(converted.getClass());
    //对象方法引用
    InstanceMethod im = new InstanceMethod();
    Converter<String,String> s = im::startWith;
    String java8 = s.convert("Java8");
    System.out.println(java8);
    System.out.println(java8.getClass());
    //构造方法引用
    PersonFactory<Person> factory = Person::new;
    Person person = factory.create("Peter", "Parker");
    System.out.println(person);
  }
}
