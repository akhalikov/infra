package akhalikov.jdbc;

import static akhalikov.utils.c3p0.DataSourceFactory.createPGSimpleDataSource;
import akhalikov.jdbc.user.User;
import akhalikov.jdbc.user.UserJdbcDao;
import akhalikov.utils.properties.PropertyUtils;

import javax.sql.DataSource;
import java.util.Properties;

public class JdbcMain {

  public static void main(String[] args) throws Exception {
    Properties properties = PropertyUtils.load();
    DataSource dataSource = createPGSimpleDataSource(properties);
    UserJdbcDao dao = new UserJdbcDao(dataSource);
    User user = User.create("John", "Doe");
    dao.insert(user);
    System.out.println("users in db: " + dao.getAll());
  }
}
