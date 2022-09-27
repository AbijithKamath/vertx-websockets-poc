package com.example.vertx.websocketspoc;

import com.example.vertx.websocketspoc.config.ConfigLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  private  String websocketPosition;
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigLoader.load(vertx)
      .onFailure(startPromise::fail)
      .onSuccess(configuration -> {
      //  websocketPosition= configuration.getInteger("webSocketPosition");
        vertx.createHttpServer()
          .webSocketHandler(new WebSocketHandler(1))
          .listen(8900, http -> {
            if (http.succeeded()) {
              startPromise.complete();
              LOG.info("HTTP server started on port 8900");
            } else {
              startPromise.fail(http.cause());
            }
          });
      });


  }
}
