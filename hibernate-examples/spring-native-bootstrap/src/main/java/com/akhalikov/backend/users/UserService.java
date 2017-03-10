package com.akhalikov.backend.users;

import com.akhalikov.backend.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserService {
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private final UserSpringDao userDao;

  public UserService(UserSpringDao userDao) {
    this.userDao = userDao;
  }

  @Transactional
  public void process() {
    LOGGER.info("Selecting users");
    List<User> users = userDao.getAll();
    LOGGER.info("Users in db: {}", users);

    User user1 = new User("Mark", "Antony");
    User user2 = new User("John", "Dohn");
    User user3 = new User("Bear", "Grylls");

    LOGGER.info("Creating new users {}, {}, {}", user1, user2, user3);
    userDao.insert(user1);
    userDao.insert(user2);
    userDao.insert(user3);

    LOGGER.info("Users in db: {}", userDao.getAll());

    LOGGER.info("Removing users");
    userDao.delete(user1.getId());
    userDao.delete(user2.getId());
    userDao.delete(user3.getId());

    userDao.insertWithReadonlyTransaction(new User("Mark", "Antony"));

    LOGGER.info("Users in db: {}", userDao.getAll());
  }
}
