package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlHelper {
  private final Connection connection;

  public SqlHelper(Connection connection) {
    this.connection = connection;
  }

  public void query(String sql) {
    try (Statement statement = connection.createStatement()) {
      statement.execute(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Integer queryForInteger(String sql) {
    try (Statement statement = connection.createStatement()) {
      try (ResultSet rs = statement.executeQuery(sql)) {
        if (rs.next()) {
          return rs.getInt(1);
        }
        return null;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
