package enums;

public enum CarTypeEnum {
  A(1),
  B(2),
  C(4),
  D(8);
  private int code;

  CarTypeEnum(int code) {
    this.code = code;
  }

  public static CarTypeEnum find(int code) {
    for (CarTypeEnum at: CarTypeEnum.values()
         ) {
      if(at.code == code) {
        return at;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }
}