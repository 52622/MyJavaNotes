package twoLambda.lambdaScopes;

import oneBasic.Formula;
import twoLambda.functionalInterfaces.Converter;

public class LambdaScopeTest {
  static int outerStaticNum;
  int outerNum;

  //Lambda表达式的作用域
  public static void main(String[] args) {

    //1.局部变量
    int num = 1; //会被隐式转换成final
    Converter<Integer,String> integerStringConverter = (from -> String.valueOf(from+num));
    integerStringConverter.convert(1);
    //num = 3;//不可以修改num

    //3. lambda不能访问接口的默认方法
    //Formula formula = (a) -> sqrt(a*100);
  }
  void testScope() {
    //2.字段和静态变量
    Converter<Integer, String> stringConverter1 = (from) -> {
      outerNum = 23;
      return String.valueOf(from);
    };

    Converter<Integer, String> stringConverter2 = (from) -> {
      outerStaticNum = 72;
      return String.valueOf(from);
    };
  }
}
