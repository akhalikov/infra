package com.akhalikov.backend.users;

import java.util.List;
import java.util.Optional;

public interface UserDao {

  Optional<User> get(int userId);

  List<User> getAll();

  void insert(User user);

  void delete(int userId);
}
