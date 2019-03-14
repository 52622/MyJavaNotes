package simpleioc;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Wheel
 * Author:   copywang
 * Date:     2019/3/13 13:10
 * Description: 测试bean
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class Wheel {
  private String brand;
  private String specification;

  public Wheel() {
  }

  public Wheel(String brand, String specification) {
    this.brand = brand;
    this.specification = specification;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getSpecification() {
    return specification;
  }

  public void setSpecification(String specification) {
    this.specification = specification;
  }

  @Override
  public String toString() {
    return "Wheel{" +
        "brand='" + brand + '\'' +
        ", specification='" + specification + '\'' +
        '}';
  }
}
