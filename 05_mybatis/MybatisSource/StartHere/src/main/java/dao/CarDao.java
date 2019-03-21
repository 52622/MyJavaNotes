package dao;

import entity.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarDao {
  Car findOne(@Param("id") int id);
  Car findOneCar(@Param("id") int id);
  List<Car> findAllCar();
}