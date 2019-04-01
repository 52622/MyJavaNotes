/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: CommandTest
 * Author:   copywang
 * Date:     2018/11/14 8:48
 * Description: 命令模式测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class CommandTest {
  public static void main(String[] args) {
    Invoker invoker = new Invoker();
    Light light = new Light();
    Command lightOnCommand = new LightOnCommand(light);
    Command lightOffCommand = new LightOffCommand(light);
    invoker.setOnCommand(lightOnCommand, 0);
    invoker.setOffCommand(lightOffCommand, 0);
    invoker.onButtonWasPushed(0);
    invoker.offButtonWasPushed(0);
  }

  //命令接口
  interface Command {
    void execute();
  }
  //具体的执行命令
  public static class LightOnCommand implements Command {
    Light light; //命令接受者

    public LightOnCommand(Light light) {
      this.light = light;
    }

    @Override
    public void execute() {
      light.on();//执行什么操作
    }
  }

  public static class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
      this.light = light;
    }

    @Override
    public void execute() {
      light.off();
    }
  }

  // 命令的接收者，真正的命令执行对象
  public static class Light {

    public void on() {
      System.out.println("Light is on!");
    }

    public void off() {
      System.out.println("Light is off!");
    }
  }

  // 使用命令对象的入口，通过对象调用命令
  public static class Invoker {
    private Command[] onCommands;
    private Command[] offCommands;
    private final int slotNum = 7;

    public Invoker() {
      this.onCommands = new Command[slotNum];
      this.offCommands = new Command[slotNum];
    }

    public void setOnCommand(Command command, int slot) {
      onCommands[slot] = command;
    }

    public void setOffCommand(Command command, int slot) {
      offCommands[slot] = command;
    }

    public void onButtonWasPushed(int slot) {
      onCommands[slot].execute();
    }

    public void offButtonWasPushed(int slot) {
      offCommands[slot].execute();
    }
  }
}
