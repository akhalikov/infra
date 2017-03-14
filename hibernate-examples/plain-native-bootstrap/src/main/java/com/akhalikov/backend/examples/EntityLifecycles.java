package com.akhalikov.backend.examples;

import com.akhalikov.backend.users.User;
import com.akhalikov.backend.utils.PropertiesFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Properties;

import static com.akhalikov.backend.utils.HibernateSessionFactoryBuilder.createSessionFactory;

class EntityLifecycles {

  public static void main(String[] args) throws Exception {
    Properties properties = PropertiesFactory.load();
    SessionFactory sessionFactory = createSessionFactory(properties);

    int userId;

    // session 1
    try (Session session = sessionFactory.openSession()) {

      // state: new
      User user = new User("Helen", "Doe");

      // state: managed, now we have user.id
      session.saveOrUpdate(user);

      userId = user.getId();
      System.out.println("user id = " + userId);
    }

    // session 2
    try (Session session = sessionFactory.openSession()) {
      session.getTransaction().begin();

      // get object in managed state
      User user = session.byId(User.class).load(userId);

      System.out.println("user in db: " + user);

      user.setLastName("Monroe");
      session.flush();
      session.getTransaction().commit();
    }
  }
}
