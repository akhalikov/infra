package com.akhalikov.backend;

import com.akhalikov.backend.users.User;
import com.akhalikov.backend.users.UserDao;
import com.akhalikov.backend.users.UserPlainJdbcDao;
import com.akhalikov.backend.utils.datasource.DataSourceFactory;
import com.akhalikov.backend.utils.properties.PropertiesFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class Main {

  public static void main(String[] args) throws Exception {
    final Properties properties = PropertiesFactory.load();

    final DataSource dataSource = DataSourceFactory.createPGSimpleDataSource(properties);

    final UserDao userDao = new UserPlainJdbcDao(dataSource);

    final User user = User.create("John", "Doe");
    userDao.insert(user);

    System.out.println("users in db: " + userDao.getAll());
  }
}
