package akhalikov.spring4webapp;

import akhalikov.utils.properties.PropertyUtils;
import akhalikov.utils.properties.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class ProdAppConfig {
  @Bean
  Settings settings() throws Exception {
    Properties properties = PropertyUtils.fromFileInSettingsDir("settings.properties");
    return new Settings(properties);
  }

  @Bean
  TestController testController() {
    return new TestController();
  }
}
