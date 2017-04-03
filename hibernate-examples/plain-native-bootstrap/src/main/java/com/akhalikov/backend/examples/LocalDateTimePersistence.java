package com.akhalikov.backend.examples;

import com.akhalikov.backend.users.User;
import com.akhalikov.backend.users.UserHibernateDao;

import java.util.concurrent.TimeUnit;

/**
 * Persisting date time w/o time zone using java.time.LocalDateTime
 */
public class LocalDateTimePersistence extends AbstractExample {

  @Override
  void play() throws Exception {
    UserHibernateDao dao = new UserHibernateDao(sessionFactory);
    User user = dao.get(1);
    print(user);

    TimeUnit.SECONDS.sleep(1);

    user.setLastName("Born");
    dao.update(user);

    print(dao.get(1));
  }

  private static void print(User user) {
    System.out.println();
    System.out.println(user + ", "
      + "created=" + user.getCreatedTs()
      + ", last updated=" + user.getUpdatedDateTime());
  }

  public static void main(String[] args) {
    new LocalDateTimePersistence().go();
  }
}
