package com.akhalikov.postgres.subscription;

import utils.SqlHelper;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generate records for subscription table
 */
class Subscriptions {
  private static final String SQL_QUERY =
      "insert into subscription (active, creation_date, user_id) " +
      "values (true, NOW(), %d)";

  private static final int MIN_ID = 7_635;
  private static final int MAX_ID = 31_449_155;
  private static final int COUNT = 23_232_079;
  private static final int NUM_THREADS = 20;
  private static final boolean ids[] = new boolean[MAX_ID+1];

  static void generate() {
    System.out.println(String.format("Generating userIds (%d, %d)", MIN_ID, MAX_ID));

    int batchSize = (MAX_ID - MIN_ID) / NUM_THREADS;
    int min = MIN_ID;
    int max = min + batchSize;
    int countPerThread = COUNT / NUM_THREADS;
    for (int i = 0; i < NUM_THREADS; i++) {
      Thread t = new Thread(new Generator(min, max, countPerThread));
      t.start();
      min = max + 1;
      max += batchSize;
    }
  }

  private static class Generator implements Runnable {
    final int min;
    final int max;
    final int count;
    final SqlHelper sqlHelper;

    Generator(int min, int max, int count) {
      this.min = min;
      this.max = max;
      this.count = count;
      this.sqlHelper = new SqlHelper(Database.connect());
    }

    @Override
    public void run() {
      System.out.println("started generating Ids from " + min + " to " + max);
      for (int k = 0; k < count; k++) {
        int userId;
        do {
          userId = getRandomIntFromRange(min, max);
        } while (ids[userId]);

        sqlHelper.query(String.format(SQL_QUERY, userId));
        ids[userId] = true;
      }
    }

    private static int getRandomIntFromRange(int min, int max) {
      return ThreadLocalRandom.current().nextInt(min, max+1);
    }
  }
}
