package com.akhalikov.backend.examples;

import com.akhalikov.backend.users.User;
import org.hibernate.Session;

class EntityLifeCycles extends AbstractExample {
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
      user.setLastName("Bobobo"); // now user is 'dirty'

      session.flush(); // expect that object will not sync, since it's detached
      session.getTransaction().commit();

      User userFromDb = session.byId(User.class).load(userId);
      assert "Monroe".equals(userFromDb.getLastName()); // should have old value
      System.out.println("user in db: " + userFromDb);

      // clear again to detach userFromDb from hibernate
      session.clear();

      // let's reattach our user to hibernate session
      // dirty user object will be saved into database
      session.saveOrUpdate(user);

      // less evident way to reattach to hibernate is merge
      // dirty user will be saved to database as with saveOrUpdate
      // session.merge(user);

      // yet another way to attach to hibernate session is this:
      // Hibernate.initialize(user);
      //
      // problem: changes in user object will be lost and replaced with data from database

      userFromDb = session.byId(User.class).load(userId);
      assert "Bobobo".equals(userFromDb.getLastName());
      System.out.println("user in db: " + userFromDb);
    }
  }

  public static void main(String[] args) {
    new EntityLifeCycles().go();
  }
}
