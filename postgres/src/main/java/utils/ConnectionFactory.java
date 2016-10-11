package utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Basic connection factory
 */
public class ConnectionFactory {

  public static Connection createConnection(String url, String username, String password) {
    Connection connection;
    try {
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(url, username, password);
      return connection;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
