package serviceprovider;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    MessageController messageController = context.getBean(MessageController.class);
    messageController.run();
  }
}
