package akhalikov.spring4webapp.beannaming;

public class MessageServiceImpl implements MessageService {

  public void printMessage(String message) {
    System.out.println("message service: " + message);
  }
}
