package tinycases;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class ArrayListForTest {
  public static void main(String[] args) {
    final int SIZE = 100000000;
    ArrayList<Integer> list = new ArrayList<Integer>(SIZE);
    for (int i = 0; i < SIZE-1; i++) {
      list.add(i);
    }
    long start = System.currentTimeMillis();
    for (int i = 0; i < list.size(); i++) {
      //do-nothing
    }
    long end = System.currentTimeMillis();
    System.out.println("for i: " + String.valueOf(end-start));//2

    long start1 = System.currentTimeMillis();
    for (int i: list
         ) {
      // do-nothing
    }
    long end1 = System.currentTimeMillis();
    System.out.println("for each " + String.valueOf(end1-start1));//152

    //linkedlist

    LinkedList<Integer> linkedList = new LinkedList<Integer>();
    for (int i = 0; i < SIZE-1; i++) {
      linkedList.add(i);
    }
    long start2 = System.currentTimeMillis();
    for (int i = 0; i < linkedList.size(); i++) {
      //do-nothing
    }
    long end2 = System.currentTimeMillis();
    System.out.println("for i: " + String.valueOf(end2-start2));//2

    long start3 = System.currentTimeMillis();
    for (int i: linkedList
    ) {
      // do-nothing
    }
    long end3 = System.currentTimeMillis();
    System.out.println("for each " + String.valueOf(end3-start3));//152
  }
}
