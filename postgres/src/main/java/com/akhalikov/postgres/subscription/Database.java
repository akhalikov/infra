package com.akhalikov.postgres.subscription;

import utils.ConnectionFactory;

import java.sql.Connection;

/**
 * PostgreSql: create Subscription schema
 */
class Database {
  private static final String URL = "jdbc:postgresql://localhost/artur";

  static final String CREATE_SUBSCRIPTION_TABLE =
      "CREATE TABLE IF NOT EXISTS subscription (" +
          "subscription_id SERIAL NOT NULL, " +
          "active BOOL NOT NULL, " +
          "creation_date timestamp NOT NULL," +
          "user_id int4 not null, " +
          "primary key (subscription_id)," +
          "unique (user_id)" +
          ");";

  static final String DROP_SUBSCRIPTION_TABLE = "DROP TABLE IF EXISTS subscription";

  static Connection connect() {
    return ConnectionFactory.createConnection(URL, "artur", "");
  }
}
