package com.akhalikov.backend;

import com.akhalikov.backend.users.User;
import com.akhalikov.backend.users.UserHibernateDao;
import static com.akhalikov.backend.utils.HibernateSessionFactoryBuilder.createSessionFactory;
import com.akhalikov.backend.utils.PropertiesFactory;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {

  public static void main(String[] args) throws IOException {
    Properties properties = PropertiesFactory.load();

    SessionFactory sessionFactory = createSessionFactory(properties);

    UserHibernateDao userDao = new UserHibernateDao(sessionFactory);

    userDao.insert(new User("John", "Doe"));

    List<User> users = userDao.getAll();

    System.out.println("users id db: " + users);

    for (User user: users) {
      System.out.println("removing user " + user);

      userDao.delete(user.getId());
    }

    System.out.println("users id db: " + userDao.getAll());
  }
}
