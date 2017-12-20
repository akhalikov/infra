package akhalikov.spring4webapp.beannaming;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Config2 {
  @Bean
  MessageService newMessageService() {
    return new NewMessageServiceImpl();
  }

  /**
   * To demonstrate, that Spring allows to have beans with the same name but different types in a context
   * @see Config1
   */
  @Bean
  String messageService() {
    return "Hey, I am evil bean and I am going to destroy your context!";
  }
}
