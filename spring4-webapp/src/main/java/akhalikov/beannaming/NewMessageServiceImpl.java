package akhalikov.spring4webapp.beannaming;

public class NewMessageServiceImpl implements MessageService {

  @Override
  public void printMessage(String message) {
    System.out.println("new message service: " + message);
  }
}
