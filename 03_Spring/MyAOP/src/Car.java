/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Car
 * Author:   copywang
 * Date:     2019/3/13 13:11
 * Description: 测试bean
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class Car {
  private String name;
  private String length;
  private String width;
  private String height;
  private Wheel wheel;

  public Car() {
  }

  public Car(String name, String length, String width, String height, Wheel wheel) {
    this.name = name;
    this.length = length;
    this.width = width;
    this.height = height;
    this.wheel = wheel;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public Wheel getWheel() {
    return wheel;
  }

  public void setWheel(Wheel wheel) {
    this.wheel = wheel;
  }

  @Override
  public String toString() {
    return "Car{" +
        "name='" + name + '\'' +
        ", length='" + length + '\'' +
        ", width='" + width + '\'' +
        ", height='" + height + '\'' +
        ", wheel=" + wheel +
        '}';
  }
}
