package com.bnbcurrencyservice.client;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class CurrencyWebSocketClient {

    private final WebSocketStompClient webSocketStompClient;
    private final CurrencySessionHandler sessionHandler;

    public CurrencyWebSocketClient(CurrencySessionHandler sessionHandler) {
        this.webSocketStompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.sessionHandler = sessionHandler;
    }

    public void connectToServer() {
        String serverUrl = "ws://localhost:8080/ws/currency-updates";
        webSocketStompClient.connect(serverUrl, sessionHandler);
    }
}