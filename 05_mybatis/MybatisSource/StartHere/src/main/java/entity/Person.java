package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class Person {
  private Integer id;
  private String name;
  private Integer age;
  private Date birthday;
}
