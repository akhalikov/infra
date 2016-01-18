package simpleservice;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@Path("/kaptcha")
public class KaptchaController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private String sessionKeyValue = null;
  private String sessionKeyDateValue = null;

  @GET
  @Produces("image/jpeg")
  public Response getKaptcha(@Context Request request) throws IOException {
    log.info("Kaptcha resource");
    final Properties props = getProperties();
    final Config config1 = new Config(props);
    final Producer kaptchaProducer = config1.getProducerImpl();
    sessionKeyValue = config1.getSessionKey();
    sessionKeyDateValue = config1.getSessionDate();
    StreamingOutput stream = (OutputStream outputStream) -> {
      String capText = kaptchaProducer.createText();
      BufferedImage bi = kaptchaProducer.createImage(capText);
      ImageIO.write(bi, "jpg", outputStream);
    };
    return Response.ok(stream).build();
  }

  private Properties getProperties() {
    final Properties props = new Properties();
    props.put("kaptcha.border", "no");
    props.put("kaptcha.textproducer.font.color", "black");
    props.put("kaptcha.textproducer.char.space", "5");
    return props;
  }
}
