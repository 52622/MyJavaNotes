import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: MementoTest
 * Author:   copywang
 * Date:     2018/11/14 10:34
 * Description: 备忘录模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class MementoTest {

  public static void main(String[] args) {
    Originator originator = new Originator();
    CareTaker careTaker = new CareTaker();
    originator.setState("State #1");
    originator.setState("State #2");
    careTaker.add(originator.saveStateToMemento());
    originator.setState("State #3");
    careTaker.add(originator.saveStateToMemento());
    originator.setState("State #4");

    System.out.println("Current State: " + originator.getState());
    originator.getStateFromMemento(careTaker.get(0));
    System.out.println("First saved State: " + originator.getState());
    originator.getStateFromMemento(careTaker.get(1));
    System.out.println("Second saved State: " + originator.getState());
  }

  public static class Memento {
    private String state;

    public Memento(String state){
      this.state = state;
    }

    public String getState(){
      return state;
    }
  }

  public static class Originator {
    private String state;

    public void setState(String state){
      this.state = state;
    }

    public String getState(){
      return state;
    }

    public Memento saveStateToMemento(){
      return new Memento(state);
    }

    public void getStateFromMemento(Memento Memento){
      state = Memento.getState();
    }
  }

  public static class CareTaker {
    private List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento state){
      mementoList.add(state);
    }

    public Memento get(int index){
      return mementoList.get(index);
    }
  }
}
