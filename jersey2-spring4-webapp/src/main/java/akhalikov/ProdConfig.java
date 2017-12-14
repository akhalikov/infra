package akhalikov;

import akhalikov.utils.properties.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class ProdConfig {
  @Bean
  TestResource testResource() {
    return new TestResource();
  }

  @Bean
  Settings settings() {
    Properties properties = new Properties();
    return new Settings(properties);
  }
}
