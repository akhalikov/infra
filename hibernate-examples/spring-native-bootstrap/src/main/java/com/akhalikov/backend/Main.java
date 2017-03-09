package com.akhalikov.backend;

import com.akhalikov.backend.config.AppConfig;
import com.akhalikov.backend.users.User;
import com.akhalikov.backend.users.UserDao;
import com.akhalikov.backend.users.UserSpringDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {

    System.out.println("Spring-Hibernate native bootstrap");

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    UserSpringDao userDao = context.getBean("userDao", UserSpringDao.class);

    System.out.println("users in db: " + userDao.getAll());

    User user1 = new User("Mark", "Antony");
    userDao.insert(user1);

    User user2 = new User("John", "Dohn");
    userDao.insert(user2);

    User user3 = new User("Bear", "Grylls");
    userDao.insert(user3);

    System.out.println("users in db: " + userDao.getAll());

    userDao.delete(user1.getId());
    userDao.delete(user2.getId());
    userDao.delete(user3.getId());

    userDao.insertWithReadonlyTransaction(new User("Mark", "Antony"));

    System.out.println("users in db: " + userDao.getAll());
  }
}
