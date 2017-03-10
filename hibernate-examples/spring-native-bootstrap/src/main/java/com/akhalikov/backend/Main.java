package com.akhalikov.backend;

import com.akhalikov.backend.config.AppConfig;
import com.akhalikov.backend.users.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {

    System.out.println("Spring-Hibernate native bootstrap");

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    UserService userService = context.getBean("userService", UserService.class);

    userService.process();
  }
}
