package com.bnbcurrencyservice.controller;

import com.bnbcurrencyservice.fetcher.service.CurrencyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/download-currencies")
    public String downloadCurrencies() {
        currencyService.downloadAndProcessCurrencies();
        return "Currencies downloaded and notified if updated";
    }
}