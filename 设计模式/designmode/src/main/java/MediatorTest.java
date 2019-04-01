import java.time.LocalDateTime;

/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: MediatorTest
 * Author:   copywang
 * Date:     2018/11/14 10:15
 * Description: 中介者模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class MediatorTest {

  public static void main(String[] args) {
    User robert = new User("Robert");
    User john = new User("John");

    robert.sendMessage("Hi! John!");
    john.sendMessage("Hello! Robert!");
  }

  public static class ChatRoom {
    public static void showMessage(User user, String message){
      System.out.println(LocalDateTime.now()
          + " [" + user.getName() +"] : " + message);
    }
  }

  public static class User {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public User(String name){
      this.name  = name;
    }

    public void sendMessage(String message){
      ChatRoom.showMessage(this,message);
    }
  }
}
