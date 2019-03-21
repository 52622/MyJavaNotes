package dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-mybatis.xml")
public class CarDaoTest implements ApplicationContextAware {

  ApplicationContext applicationContext;

  @Autowired
  private CarDao carDao;

  @Before
  public void printBeanInfo() {
    ListableBeanFactory lbf = applicationContext;
    String[] beanNames = lbf.getBeanDefinitionNames();
    Arrays.sort(beanNames);

    System.out.println();
    System.out.println("----------------☆ bean name ☆---------------");
    Arrays.asList(beanNames).subList(0, 5).forEach(System.out::println);
    System.out.println();

    CarDao carDao = (CarDao) applicationContext.getBean("carDao");

    System.out.println("-------------☆ bean class info ☆--------------");
    System.out.println("AuthorDao  Class: " + carDao.getClass());
    System.out.println("\n--------xxxx---------xxxx---------xxx---------\n");
  }

  @Test
  public void test() {
    System.out.println(carDao.findOneCar(1).toString());
    System.out.println(carDao.findOne(1).toString());
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}