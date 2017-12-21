package akhalikov;

import akhalikov.utils.properties.PropertyUtils;
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
  Settings settings() throws Exception {
    Properties properties = PropertyUtils.fromFileInSettingsDir("settings.properties");
    return new Settings(properties);
  }
}
