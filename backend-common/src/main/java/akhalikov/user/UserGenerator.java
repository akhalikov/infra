package akhalikov.user;

import static java.lang.System.currentTimeMillis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class UserGenerator {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserGenerator.class);
  private static final int MAX_USERS = 1_000_000;

  private final UserDao userDao;

  public UserGenerator(UserDao userDao) {
    this.userDao = userDao;
  }

  public void generateUsers(int numOfUsers) {
    LOGGER.info("start generating users, n={}", numOfUsers);
    int i = 0;
    for (; i <= numOfUsers && i <= MAX_USERS; i++) {
      User user = generateUser();
      userDao.saveUser(user);
      if (i > 1000 && i % 1000 == 0) {
        LOGGER.info("saved {} users", i+1);
      }
    }
    LOGGER.info("done generating users, {}", i+1);
  }

  private static User generateUser() {
    User user = new User();
    long generatedId = nextLong(MAX_USERS);
    user.firstName = "first_name_" + generatedId;
    user.lastName = "last_name_" + generatedId;
    user.birthDate = new Date(currentTimeMillis() - nextLong(1_000_000_000_000L));
    user.createdTime = Instant
        .ofEpochMilli(currentTimeMillis() - nextLong(1_000_000_000))
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
    return user;
  }

  private static long nextLong(long bound) {
    return ThreadLocalRandom.current().nextLong(bound);
  }
}
