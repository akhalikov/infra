package com.akhalikov.backend.examples;

import com.akhalikov.backend.users.User;
import static com.akhalikov.backend.utils.HibernateSessionFactoryBuilder.createSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

class EntityLifecycles {

  public static void main(String[] args) throws Exception {
    SessionFactory sessionFactory = createSessionFactory();

    int userId;

    // session 1
    try (Session session = sessionFactory.openSession()) {

      // state: new
      User user = new User("Helen", "Doe");

      // state: managed, now we have user.id
      session.saveOrUpdate(user);

      userId = user.getId(); // not null
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

      user = session.byId(User.class).load(userId);
      assert "Monroe".equals(user.getLastName()); // last name is updated
      System.out.println("user: " + user);

      // clearing session makes user object detached
      session.clear();

      session.getTransaction().begin();
      user.setLastName("Bobobo");

      session.flush(); // expect that object will not sync, since it's detached
      session.getTransaction().commit();

      user = session.byId(User.class).load(userId);
      System.out.println("detached user: " + user);
      assert "Monroe".equals(user.getLastName()); // should have old value
    }

    sessionFactory.close();
  }
}
