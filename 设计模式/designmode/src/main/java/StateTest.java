/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: StateTest
 * Author:   copywang
 * Date:     2018/11/14 11:19
 * Description: 状态模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class StateTest {

  public static void main(String[] args) {
    Context context = new Context();

    StartState startState = new StartState();
    startState.doAction(context);

    System.out.println(context.getState().toString());

    StopState stopState = new StopState();
    stopState.doAction(context);

    System.out.println(context.getState().toString());
  }

  // 状态接口
  public interface State {
    public void doAction(Context context);
  }

  public static class StartState implements State {

    public void doAction(Context context) {
      System.out.println("Player is in start state");
      context.setState(this);
    }

    public String toString(){
      return "Start State";
    }
  }

  public static class StopState implements State {

    public void doAction(Context context) {
      System.out.println("Player is in stop state");
      context.setState(this);
    }

    public String toString(){
      return "Stop State";
    }
  }

  //状态改变，行为就改变
  public static class Context {
    private State state;

    public Context(){
      state = null;
    }

    public void setState(State state){
      this.state = state;
    }

    public State getState(){
      return state;
    }
  }
}
