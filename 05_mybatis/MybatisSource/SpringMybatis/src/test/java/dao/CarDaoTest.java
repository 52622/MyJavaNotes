package dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-mybatis.xml")
public class CarDaoTest {

  @Autowired
  private CarDao carDao;

  @Test
  public void test() {
    System.out.println(carDao.findOne(1).toString());
  }
}