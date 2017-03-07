package com.akhalikov.backend;

import com.akhalikov.backend.config.AppConfig;
import com.akhalikov.backend.users.UserDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {

    System.out.println("Spring-Hibernate native bootstrap");

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    UserDao usersDao = context.getBean("userDao", UserDao.class);

    System.out.println("users in db: " + usersDao.getAll());
  }
}
