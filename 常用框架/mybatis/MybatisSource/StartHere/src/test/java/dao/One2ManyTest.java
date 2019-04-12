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

public class One2ManyTest {
  private SqlSessionFactory factory;

  @Before
  public void prepare() throws IOException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    factory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Test
  public void testOne2Many() throws IOException {
    SqlSession session = factory.openSession();
    try {
      PersonDao personDao = session.getMapper(PersonDao.class);
      Person person = personDao.findOne(1);
      System.out.println(person.toString());
      System.out.println("++++++++++++++++++++++++++");
      System.out.println(Arrays.toString(person.getCarList().toArray()));
    } finally {
      session.commit();
      session.close();
    }
  }
}
