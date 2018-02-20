package hello.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class ServerMain extends AbstractVerticle {

  public static void main(String[] args) {
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    vertx
        .createHttpServer()
        .requestHandler(request -> request.response()
            .putHeader("content-type", "text/html")
            .end("Hello VertX")).listen(8080);
  }
}
