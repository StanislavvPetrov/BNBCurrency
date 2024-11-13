package com.bnbcurrencyservice.client;

import com.bnbcurrencyservice.consumer.model.CurrencyRateDTO;
import com.bnbcurrencyservice.consumer.service.CurrencyStorageService;
import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CurrencySessionHandler extends StompSessionHandlerAdapter {

    private final CurrencyStorageService currencyStorageService;

    public CurrencySessionHandler(CurrencyStorageService currencyStorageService) {
        this.currencyStorageService = currencyStorageService;
    }

    @Override
    public void handleFrame(org.springframework.messaging.simp.stomp.StompHeaders headers, Object payload) {
        try {
            CurrencyRateDTO currencyRateDTO = (CurrencyRateDTO) payload;
            CurrencyRate currencyRate = convertToCurrencyRate(currencyRateDTO);
            currencyStorageService.saveCurrencyRate(currencyRate);
        } catch (Exception e) {
            System.err.println("Failed to process message: " + payload);
            e.printStackTrace();
        }
    }

    private CurrencyRate convertToCurrencyRate(CurrencyRateDTO dto) {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setCode(dto.getCode());
        currencyRate.setCurrDate(LocalDate.parse(dto.getCurrDate()));
        currencyRate.setNameBg(dto.getNameBg());
        currencyRate.setNameEn(dto.getNameEn());
        currencyRate.setRate(dto.getRate());
        currencyRate.setRatio(dto.getRatio());
        currencyRate.setReverseRate(dto.getReverseRate());
        currencyRate.setDownloadTime(LocalDate.now().atStartOfDay());
        return currencyRate;
    }
}
