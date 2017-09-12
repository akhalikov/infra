package akhalikov.jdbc.user;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class UserJdbcDao {
  private final DataSource dataSource;

  public UserJdbcDao(DataSource dataSource) {
    this.dataSource = Objects.requireNonNull(dataSource);
  }

  public Optional<User> get(int userId) {
    try (Connection connection = dataSource.getConnection()) {

      String sql = "SELECT user_id, first_name, last_name FROM users WHERE user_id = ?";

      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, userId); // problem: positional arguments

        try (ResultSet resultSet = statement.executeQuery()) {

          // a ResultSet cursor is initially positioned before the first row
          // By calling next() we make the first row the current row.
          boolean userExists = resultSet.next();

          if (!userExists) {
            return Optional.empty();
          } else {
            return Optional.of(
                User.existing(userId,
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")));
          }
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("failed to get user by id " + userId, e);
    }
  }

  public Set<User> getAll() {
   try (Connection connection = dataSource.getConnection()) {
     try (Statement statement = connection.createStatement()) {

       String sql = "SELECT user_id, first_name, last_name FROM users";
       try (ResultSet resultSet = statement.executeQuery(sql)) {

         Set<User> users = new HashSet<>();
         while (resultSet.next()) {
           User user = User.existing(
               resultSet.getInt("user_id"),
               resultSet.getString("first_name"),
               resultSet.getString("last_name")
           );
           users.add(user);
         }

         return users;
       }
     }
   } catch (SQLException e) {
     throw new RuntimeException("failed to get all users", e);
   }
  }

  public void insert(User user) {
    if (user.getId() != null) {
      // problem: runtime exception
      // can it be moved to compile time ?
      throw new IllegalArgumentException("can not save user with existing id " + user);
    }

    try (Connection connection = dataSource.getConnection()) {

      String sql = "INSERT INTO users (first_name, last_name) VALUES (?, ?)";

      try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.executeUpdate();

        try (ResultSet resultSet = statement.getGeneratedKeys()) {

          // need to move cursor forward to access the first row
          resultSet.next();

          user.setId(resultSet.getInt(1)); // problem: what if user is already in some set ?
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("failed to save user " + user, e);
    }
  }

  public void delete(int userId) {
    try (Connection connection = dataSource.getConnection()) {

      String sql = "DELETE FROM users WHERE user_id = ?";

      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, userId);
        statement.executeUpdate();
      }

    } catch (SQLException e) {
      throw new RuntimeException("failed to delete user with id = " + userId, e);
    }
  }
}
