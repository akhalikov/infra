package akhalikov.jdbc.jetty;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebappMain {

  public static void main(String[] args) throws Exception {
    new ClassPathXmlApplicationContext("appConfig.xml");
  }
}
