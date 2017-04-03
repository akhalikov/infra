package com.akhalikov.backend.examples;

import com.akhalikov.backend.users.User;
import com.akhalikov.backend.users.UserHibernateDao;

import java.time.ZonedDateTime;

/**
 * Persisting date time with time zone using java.time.ZonedDateTime
 */
public class ZonedDateTimePersistence extends AbstractExample {
  @Override
  void play() throws Exception {
    UserHibernateDao dao = new UserHibernateDao(sessionFactory);

    User user = dao.get(1);
    print(user);

    user.setDateTime(ZonedDateTime.now());
    dao.update(user);

    print(dao.get(1));
  }

  private static void print(User user) {
    System.out.println();
    System.out.println(user + ", "
      + "created=" + user.getCreatedTs()
      + ", date_time=" + user.getDateTime());
  }

  public static void main(String[] args) {
    new ZonedDateTimePersistence().go();
  }
}
