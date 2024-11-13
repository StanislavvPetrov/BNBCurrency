package com.bnbcurrencyservice.config;

import com.bnbcurrencyservice.client.CurrencyWebSocketClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketClientConfig {

    private final CurrencyWebSocketClient currencyWebSocketClient;

    public WebSocketClientConfig(CurrencyWebSocketClient currencyWebSocketClient) {
        this.currencyWebSocketClient = currencyWebSocketClient;
    }

    public void startWebSocketConnection() {
        currencyWebSocketClient.connectToServer();
    }
}