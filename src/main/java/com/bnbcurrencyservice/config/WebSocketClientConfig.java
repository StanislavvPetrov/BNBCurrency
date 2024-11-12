package com.bnbcurrencyservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class WebSocketClientConfig {

    public WebSocketStompClient webSocketStompClient(StompSessionHandler sessionHandler) {
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.connect("ws://localhost:8080/ws/currency-updates", sessionHandler);
        return stompClient;
    }
}
