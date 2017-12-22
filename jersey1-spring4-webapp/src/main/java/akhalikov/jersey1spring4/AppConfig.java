package akhalikov.jersey1spring4;

import akhalikov.jersey1spring4.rest.TestResource;
import akhalikov.utils.properties.PropertyUtils;
import akhalikov.utils.properties.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AppConfig {
  @Bean
  Settings settings() throws Exception {
    Properties properties = PropertyUtils.fromFileInSettingsDir("app.properties");
    return new Settings(properties);
  }

  @Bean(name = "serviceName")
  String serviceName(Settings settings) {
    String serviceName = settings.getString("serviceName");
    System.out.println("serviceName=" + serviceName);
    return serviceName;
  }

  @Bean
  TestResource testResource(String serviceName) {
    return new TestResource(serviceName);
  }
}
