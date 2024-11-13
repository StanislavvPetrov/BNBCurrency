package com.bnbcurrencyservice.fetcher.controller;

import com.bnbcurrencyservice.fetcher.service.CurrencyFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyFetchService currencyFetchService;

    @Autowired
    public CurrencyController(CurrencyFetchService currencyFetchService) {
        this.currencyFetchService = currencyFetchService;
    }

    @GetMapping("/download-currencies")
    public ResponseEntity<String> downloadCurrencies() throws Exception {
        boolean updated = currencyFetchService.fetchAndStoreCurrencies();
        if (updated) {
            return ResponseEntity.ok("Currency data updated and broadcasted.");
        } else {
            return ResponseEntity.ok("No new currency data available.");
        }
    }
}
