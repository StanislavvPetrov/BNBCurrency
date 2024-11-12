package com.bnbcurrencyservice.consumer.service;

import com.bnbcurrencyservice.consumer.repository.CurrencyRateRepository;
import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import org.springframework.stereotype.Service;

@Service
public class CurrencyStorageService {

    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyStorageService(CurrencyRateRepository currencyRateRepository) {
        this.currencyRateRepository = currencyRateRepository;
    }

    public void saveCurrencyRate(CurrencyRate currencyRate) {
        currencyRateRepository.save(currencyRate);
    }
}
