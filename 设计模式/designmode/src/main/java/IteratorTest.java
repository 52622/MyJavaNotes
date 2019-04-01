/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: IteratorTest
 * Author:   copywang
 * Date:     2018/11/13 10:25
 * Description: 迭代器模式测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class IteratorTest {

  public static void main(String[] args) {
    NameRepository namesRepository = new NameRepository();

    for(Iterator iter = namesRepository.getIterator(); iter.hasNext();){
      String name = (String)iter.next();
      System.out.println("Name : " + name);
    }
  }

  public static interface Iterator {
    public boolean hasNext();
    public Object next();
  }

  public static interface Container { //List
    public Iterator getIterator();
  }

  public static class NameRepository implements Container { //ArrayList
    public String names[] = {"Robert", "John", "Julie", "Lora"};

    @Override
    public Iterator getIterator() {
      return new NameIterator();
    }

    private class NameIterator implements Iterator {

      int index;

      @Override
      public boolean hasNext() {
        if (index < names.length) {
          return true;
        }
        return false;
      }

      @Override
      public Object next() {
        if (this.hasNext()) {
          return names[index++];
        }
        return null;
      }
    }
  }
}
