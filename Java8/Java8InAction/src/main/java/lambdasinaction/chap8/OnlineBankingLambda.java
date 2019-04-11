package lambdasinaction.chap8;

import java.util.function.Consumer;


public class OnlineBankingLambda {

    // 函数式接口参数
    public static void main(String[] args) {
        new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!" + c.toString()));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // dummy Customer class
    static private class Customer {}
    // dummy Database class
    static private class Database{
        static Customer getCustomerWithId(int id){ return new Customer();}
    }
}
