package com.akhalikov.backend.examples;

import com.akhalikov.backend.users.User;
import org.hibernate.Session;

class EntityLifecycles extends AbstractExample {
  @Override
  void play() throws Exception {
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

      User userFromDb = session.byId(User.class).load(userId);
      System.out.println("user in db: " + userFromDb);
      assert "Monroe".equals(userFromDb.getLastName()); // should have old value

      // let's reattach our user to hibernate session
      session.merge(user);

      userFromDb = session.byId(User.class).load(userId);
      assert "Bobobo".equals(userFromDb.getLastName());
      System.out.println("user in db: " + userFromDb);
    }
  }

  public static void main(String[] args) {
    new EntityLifecycles().go();
  }
}
