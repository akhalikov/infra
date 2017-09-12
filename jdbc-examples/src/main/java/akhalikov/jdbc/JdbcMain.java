package akhalikov.jdbc;

import static akhalikov.datasource.DataSourceFactory.createPGSimpleDataSource;
import akhalikov.jdbc.user.User;
import akhalikov.jdbc.user.UserJdbcDao;
import akhalikov.utils.properties.PropertiesFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class JdbcMain {

  public static void main(String[] args) throws Exception {
    Properties properties = PropertiesFactory.load();
    DataSource dataSource = createPGSimpleDataSource(properties);
    UserJdbcDao dao = new UserJdbcDao(dataSource);
    User user = User.create("John", "Doe");
    dao.insert(user);
    System.out.println("users in db: " + dao.getAll());
  }
}
