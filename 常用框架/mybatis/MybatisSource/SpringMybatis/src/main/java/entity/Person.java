package entity;

import enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor //要求要有无参构造
@ToString
public class Person {
  private Integer id;
  private String name;
  private Integer age;
  private Date birthday;
  private SexEnum sex;
  private List<Car> carList;
}
