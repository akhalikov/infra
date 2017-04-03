package com.akhalikov.backend.users;

import java.util.List;

public interface UserDao {

  User get(int userId);

  List<User> getAll();

  void insert(User user);

  void delete(int userId);
}
