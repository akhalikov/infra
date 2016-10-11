package com.akhalikov.postgres.subscription;

import utils.SqlHelper;

import java.sql.Connection;

public class Main {
  private static final Connection connection = Database.connect();
  private static final SqlHelper sqlHelper = new SqlHelper(connection);

  private static final int BATCH_SIZE = 100;

  public static void main(String[] args) {
    update();
    //process();
  }

  private static void process() {
    int minUserId = sqlHelper.queryForInteger("select min(user_id) from subscription");
    int maxUserId = sqlHelper.queryForInteger("select max(user_id) from subscription");
    int countUsers = sqlHelper.queryForInteger("select count(user_id) from subscription");
    System.out.println(String.format("min=%d max=%d count=%d", minUserId, maxUserId, countUsers));

    int countBatches = (maxUserId - minUserId) / BATCH_SIZE;
    if ((maxUserId - minUserId) % BATCH_SIZE != 0) {
      countBatches += 1;
    }
    System.out.println("batches=" + countBatches);

    String sql = "select user_id from subscription where user_id between %d and %d and active=true";

    int fromUserId = minUserId;
    int toUserId = minUserId + BATCH_SIZE;
    for (int i = 0; i < countBatches; i++) {
      System.out.println(String.format(sql, fromUserId, toUserId));
      fromUserId = toUserId;
      toUserId += BATCH_SIZE;
    }
  }

  private static void update() {
    createSchema();
    Subscriptions.generate();
  }

  private static void createSchema() {
    System.out.print("Creating schema...");
    sqlHelper.query(Database.DROP_SUBSCRIPTION_TABLE);
    sqlHelper.query(Database.CREATE_SUBSCRIPTION_TABLE);
    System.out.print("OK\n");
  }
}
