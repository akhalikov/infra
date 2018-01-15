package akhalikov.rs.hello;

import javax.inject.Named;

@Named
public class HelloService {

  String getGreeting(String name) {
    return String.format("Hello, %s!", name);
  }
}
