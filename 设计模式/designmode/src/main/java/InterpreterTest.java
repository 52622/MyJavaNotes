/**
 * Copyright (C), 2018-2018, XXX有限公司
 * FileName: InterpreterTest
 * Author:   copywang
 * Date:     2018/11/14 9:08
 * Description: 解释器模式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class InterpreterTest {

  public static void main(String[] args) {
    // 使用 Expression 类来创建规则
    Expression isMale = getMaleExpression();
    Expression isMarriedWoman = getMarriedWomanExpression();

    // 解析
    System.out.println("John is male? " + isMale.interpret("John"));
    System.out.println("Julie is a married women? "
        + isMarriedWoman.interpret("Married Julie"));
  }

  // 表达式接口
  public interface Expression {
    public boolean interpret(String context);
  }

  // 实体类
  public static class TerminalExpression implements Expression {

    private String data;

    public TerminalExpression(String data){
      this.data = data;
    }

    @Override
    public boolean interpret(String context) {
      if(context.contains(data)){
        return true;
      }
      return false;
    }
  }

  public static class OrExpression implements Expression {

    private Expression expr1 = null;
    private Expression expr2 = null;

    public OrExpression(Expression expr1, Expression expr2) {
      this.expr1 = expr1;
      this.expr2 = expr2;
    }

    @Override
    public boolean interpret(String context) {
      return expr1.interpret(context) || expr2.interpret(context);
    }
  }

  public static class AndExpression implements Expression {

    private Expression expr1 = null;
    private Expression expr2 = null;

    public AndExpression(Expression expr1, Expression expr2) {
      this.expr1 = expr1;
      this.expr2 = expr2;
    }

    @Override
    public boolean interpret(String context) {
      return expr1.interpret(context) && expr2.interpret(context);
    }
  }

  //规则：Robert 和 John 是男性
  public static Expression getMaleExpression(){
    Expression robert = new TerminalExpression("Robert");
    Expression john = new TerminalExpression("John");
    return new OrExpression(robert, john);
  }

  //规则：Julie 是一个已婚的女性
  public static Expression getMarriedWomanExpression(){
    Expression julie = new TerminalExpression("Julie");
    Expression married = new TerminalExpression("Married");
    return new AndExpression(julie, married);
  }
}
