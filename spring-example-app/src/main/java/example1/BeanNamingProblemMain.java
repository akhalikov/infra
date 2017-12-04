package example1;

import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanNamingProblemMain {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config1.class, Config2.class);

    // MessageService basicMessageService is overridden by String bean
    String wrongBean = context.getBean("messageService", String.class);
    System.out.println(wrongBean);

    try {
      MessageService correctBean = context.getBean("messageService", MessageService.class);
      correctBean.printMessage("obtained the correct bean");

    } catch (BeanNotOfRequiredTypeException e) {
      System.out.println("Spring expects unique bean names: " + e.getMessage());
    }

    // Although MessageService bean is specified to be injected into MessageController, another bean is injected
    // This is because the original bean is silently overridden by String bean-pretender
    MessageController messageController = context.getBean(MessageController.class);
    messageController.run();
  }
}
