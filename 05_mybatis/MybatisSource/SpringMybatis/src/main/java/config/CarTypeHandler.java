package config;

import enums.CarTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarTypeHandler extends BaseTypeHandler<CarTypeEnum> {
  @Override
  public void setNonNullParameter(PreparedStatement preparedStatement, int i, CarTypeEnum carTypeEnum, JdbcType jdbcType) throws SQLException {
    // 获取枚举的 code 值，并设置到 PreparedStatement 中
    preparedStatement.setInt(i, carTypeEnum.getCode());
  }

  @Override
  public CarTypeEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
    // 从 ResultSet 中获取 code
    int code = resultSet.getInt(s);
    // 解析 code 对应的枚举，并返回
    return CarTypeEnum.find(code);
  }

  @Override
  public CarTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
    int code = resultSet.getInt(i);
    return CarTypeEnum.find(code);
  }

  @Override
  public CarTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
    int code = callableStatement.getInt(i);
    return CarTypeEnum.find(code);
  }
}
