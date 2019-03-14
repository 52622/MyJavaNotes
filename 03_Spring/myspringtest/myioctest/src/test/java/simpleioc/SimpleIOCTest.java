package simpleioc;

import org.junit.Test;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SimpleIOCTest
 * Author:   copywang
 * Date:     2019/3/13 13:13
 * Description: 测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SimpleIOCTest {
  @Test
  public void getBean() throws Exception {
    String location = SimpleIOC.class.getClassLoader().getResource("simpleIOC.xml").getFile();
    SimpleIOC bf = new SimpleIOC(location);
    Wheel wheel = (Wheel) bf.getBean("wheel");
    System.out.println(wheel);
    Car car = (Car) bf.getBean("car");
    System.out.println(car);
  }
}
