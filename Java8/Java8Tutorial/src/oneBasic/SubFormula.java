package oneBasic;

public class SubFormula implements Formula {
  @Override
  public double calculate(int a) {
    return 1;
  }

  @Override
  public double sqrt(int a) {
    return 2;
  }
}
