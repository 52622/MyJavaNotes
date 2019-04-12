package dao;

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

public class PersonDaoTest {
  private SqlSessionFactory factory;

  @Before
  public void prepare() throws IOException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    factory = new SqlSessionFactoryBuilder().build(inputStream);
    inputStream.close();
  }

  @Test
  public void testMyBatis() throws IOException {
    SqlSession session = factory.openSession();
    try {
      PersonDao personDao = session.getMapper(PersonDao.class);
      List<Person> personList = personDao.findByNameAndCreateTime("cojj", "2019-03-20");
      System.out.println(Arrays.toString(personList.toArray()));
    } finally {
      session.commit();
      session.close();
    }
  }
}
