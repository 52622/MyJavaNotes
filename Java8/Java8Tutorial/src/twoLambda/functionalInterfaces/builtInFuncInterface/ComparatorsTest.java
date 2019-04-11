package twoLambda.functionalInterfaces.builtInFuncInterface;

import twoLambda.methodReferences.Person;

import java.util.Comparator;

public class ComparatorsTest {
  public static void main(String[] args) {
    Comparator<Person> comparator = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());

    Person p1 = new Person("a", "Doe");
    Person p2 = new Person("b", "Wonderland");

    System.out.println(comparator.compare(p1, p2));             // -1
    System.out.println(comparator.reversed().compare(p1, p2));  // 1
  }
}
