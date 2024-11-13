package com.bnbcurrencyservice.fetcher.service;

import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import com.bnbcurrencyservice.fetcher.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyFetchService {

    private final XmlParserService xmlParserService;
    private final CurrencyRateRepository currencyRateRepository;
    private final WebSocketBroadcastService webSocketBroadcastService;
    private String lastFetchedXmlHash = "";

    @Autowired
    public CurrencyFetchService(XmlParserService xmlParserService,
                                CurrencyRateRepository currencyRateRepository,
                                WebSocketBroadcastService webSocketBroadcastService) {
        this.xmlParserService = xmlParserService;
        this.currencyRateRepository = currencyRateRepository;
        this.webSocketBroadcastService = webSocketBroadcastService;
    }

    public boolean fetchAndStoreCurrencies() throws Exception {
        String xmlData = xmlParserService.fetchCurrencyData();
        String currentHash = String.valueOf(xmlData.hashCode());

        if (!currentHash.equals(lastFetchedXmlHash)) {
            lastFetchedXmlHash = currentHash;
            List<CurrencyRate> rates = xmlParserService.parseXmlData(xmlData);
            currencyRateRepository.saveAll(rates);
            webSocketBroadcastService.broadcastRates(rates);
            return true;
        }
        return false;
    }
}