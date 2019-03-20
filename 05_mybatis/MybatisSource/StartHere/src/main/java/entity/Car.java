package entity;

import enums.CarTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor //要求要有无参构造
@ToString
public class Car {
  private String name;
  private CarTypeEnum type;
  private Long price;
}
