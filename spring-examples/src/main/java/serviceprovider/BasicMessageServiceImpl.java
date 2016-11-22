package serviceprovider;

class BasicMessageServiceImpl implements MessageService {
  public void printMessage(String message) {
    System.out.println("BasicMessageServiceImpl: " + message);
  }
}
