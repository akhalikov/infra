package com.akhalikov.backend;

import com.akhalikov.backend.users.User;
import com.akhalikov.backend.users.UserHibernateDao;
import static com.akhalikov.backend.utils.HibernateUtils.createSessionFactory;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {
    System.out.println("Hibernate native bootstrap");

    // the application should have only one SF per database
    // as it is very expensive to create
    SessionFactory sessionFactory = createSessionFactory();

    UserHibernateDao userDao = new UserHibernateDao(sessionFactory);

    userDao.insert(new User("John", "Doe"));

    List<User> users = userDao.getAll();

    System.out.println("users id db: " + users);

    for (User user: users) {
      System.out.println("removing user " + user);

      userDao.delete(user.getId());
    }

    System.out.println("users id db: " + userDao.getAll());

    sessionFactory.close();
  }
}
