package com.example.vertx.websocketspoc;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WebSocketHandler implements Handler<ServerWebSocket> {

  private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
  private final Map<Integer, ServerWebSocket> connectedClients = new HashMap<>();
  private Integer websocketPosition;
  public WebSocketHandler(Integer websocketPosition) {
    websocketPosition=websocketPosition;
  }


  @Override
  public void handle(final ServerWebSocket ws) {
    log.info("opening websocket connection {}, {} ",ws.path(),ws.textHandlerID());
    ws.accept(); //ssl handshake
    register(ws);
    ws.writeTextMessage("connected");
    ws.exceptionHandler(error->log.error("failed {}",error));

    ws.frameHandler(received->{
      String message = received.textData();
      log.info("received message : {} from {} ",message,ws.textHandlerID());
      connectedClients.get(1).writeTextMessage("received message is :  "+ message);

      //sockjs implementtion in vertx3
      //sockjs implementtion in vertx7
      //sockjs implementtion in vertx8


    });

  }
  public void register(final ServerWebSocket ws) {
    AtomicInteger count=new AtomicInteger(0);
    connectedClients.put(count.incrementAndGet(), ws);
  }

  public void unregister(final ServerWebSocket ws) {
    connectedClients.remove(ws.textHandlerID());
  }


}
