package akhalikov.spring4webapp;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

  @RequestMapping(value = "/test", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseBody
  public String test() {
    return "hello";
  }
}
