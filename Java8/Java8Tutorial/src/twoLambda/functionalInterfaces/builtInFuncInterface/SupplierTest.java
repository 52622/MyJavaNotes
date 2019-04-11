package twoLambda.functionalInterfaces.builtInFuncInterface;

import twoLambda.methodReferences.Person;

import java.util.function.Supplier;

public class SupplierTest {
  public static void main(String[] args) {
    Supplier<Person> personSupplier = Person::new;
    System.out.println(personSupplier.get().getClass());
  }
}
