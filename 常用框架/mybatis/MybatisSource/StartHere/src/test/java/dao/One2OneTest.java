package dao;

import entity.Car;
import entity.Person;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class One2OneTest {
  private SqlSessionFactory factory;

  @Before
  public void prepare() throws IOException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    factory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Test
  public void testOne2One() throws IOException {
    SqlSession session = factory.openSession();
    try {
      CarDao carDao = session.getMapper(CarDao.class);
      Car car = carDao.findOne(1);
      Person person = car.getPerson();
      System.out.println(car.toString());
      System.out.println("++++++++++++++++++++++++++");
      System.out.println(person.toString());
    } finally {
      session.commit();
      session.close();
    }
  }

  @Test
  public void testOne() throws IOException {
    SqlSession session = factory.openSession();
    try {
      CarDao carDao = session.getMapper(CarDao.class);
      Car car = carDao.findOneCar(1);
      Person person = car.getPerson();
      System.out.println(car.toString());
      System.out.println("++++++++++++++++++++++++++");
      System.out.println(person.toString());
    } finally {
      session.commit();
      session.close();
    }
  }

  @Test
  public void testList() throws IOException {
    SqlSession session = factory.openSession();
    try {
      CarDao carDao = session.getMapper(CarDao.class);
      List<Car> cars = carDao.findAllCar();
      System.out.println("++++++++++++++++++++++++++");
      System.out.println(Arrays.toString(cars.toArray()));
    } finally {
      session.commit();
      session.close();
    }
  }
}
