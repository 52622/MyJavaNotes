package simple;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class hashMapTest {
  @Test
  public void hashMapTest(){
    long star = System.currentTimeMillis();

    Set<Integer> hashset = new HashSet<>(100) ;
//    for (int i = 0; i < 100; i++) {
    for (int i = 0; i < 10000000; i++) {
      hashset.add(i) ;
    }
    Assert.assertTrue(hashset.contains(1));
    Assert.assertTrue(hashset.contains(2));
    Assert.assertTrue(hashset.contains(3));

    long end = System.currentTimeMillis();
    System.out.println("执行时间：" + (end - star));
  }
}
