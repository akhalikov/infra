package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TerminatorQuoter implements Quoter {

  private String message;

  public void sayQuote() {
    System.out.println("message = " + message);
  }

  public static void main(String[] args) {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("context.xml");
    Quoter quoter = applicationContext.getBean(Quoter.class);
    quoter.sayQuote();
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
