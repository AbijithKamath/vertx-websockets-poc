package com.example.vertx.websocketspoc;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {
  private static final Logger log = LoggerFactory.getLogger(TestMainVerticle.class);
  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }


  @Test
  void connectToWebSocketServer(Vertx vertx, VertxTestContext context) throws Throwable {
    var client = vertx.createHttpClient();
    client.webSocket(8900, "localhost","/")
      .onFailure(context::failNow)
      .onComplete(context.succeeding(ws -> {
          ws.handler(data -> {
            final var receivedData = data.toString();
            log.debug("Received message: {}", receivedData);
            assertEquals("connected", receivedData);
          //ws.writeBinaryMessage(Buffer.buffer("Hello world"));
            client.close();
            context.completeNow();
          });
        })
      );
  }




}
