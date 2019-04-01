import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: SingletonReflection
 * Author:   copywang
 * Date:     2018/11/13 19:36
 * Description: 单例模式反射攻击
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SingletonReflection {
  public static void main(String[] args) throws Exception {
    //反射获取构造方法
    Constructor<SingletonTest04> con = SingletonTest04.class.getDeclaredConstructor();
    //设置构造方法可访问
    con.setAccessible(true);
    //构建对象
    SingletonTest04 fristInstance = con.newInstance();
    SingletonTest04 secondInstance = con.newInstance();
    System.out.println(fristInstance.equals(secondInstance));//false

//抛出异常 java.lang.NoSuchMethodException: SingletonTest05.<init>()
//    Constructor<SingletonTest05> enumCon = SingletonTest05.class.getDeclaredConstructor();
  }
}
