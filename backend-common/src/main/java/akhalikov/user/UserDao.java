package akhalikov.user;

public interface UserDao {

  User get(int userId);

  void save(User user);

  void delete(int userId);
}
