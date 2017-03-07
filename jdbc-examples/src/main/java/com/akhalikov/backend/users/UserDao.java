package com.akhalikov.backend.users;

import java.util.Optional;
import java.util.Set;

public interface UserDao {

  Optional<User> get(int userId);

  Set<User> getAll();

  void insert(User user);

  void delete(int userId);
}
