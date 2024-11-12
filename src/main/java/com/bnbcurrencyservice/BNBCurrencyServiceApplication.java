package com.bnbcurrencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BNBCurrencyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BNBCurrencyServiceApplication.class, args);
    }
}
