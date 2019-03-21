package dao;

import entity.Person;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonDao {
  List<Person> findByNameAndCreateTime(@Param("name") String name, @Param("birthday") String birthday);
  // findOne
  Person findOne(@Param("id") int id);
}