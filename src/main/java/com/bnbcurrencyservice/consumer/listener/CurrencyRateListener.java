package com.bnbcurrencyservice.consumer.listener;

import com.bnbcurrencyservice.consumer.model.CurrencyRateDTO;
import com.bnbcurrencyservice.consumer.service.CurrencyStorageService;
import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyRateListener extends TextWebSocketHandler {

    private final CurrencyStorageService currencyStorageService;
    private final ObjectMapper objectMapper;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            CurrencyRateDTO currencyRateDTO = objectMapper.readValue(message.getPayload(), CurrencyRateDTO.class);
            CurrencyRate currencyRate = convertToEntity(currencyRateDTO);
            currencyStorageService.saveCurrencyRate(currencyRate);
            log.info("Currency rate saved: {}", currencyRate);

        } catch (Exception e) {
            log.error("Failed to process WebSocket message: {}", message.getPayload(), e);
        }
    }

    private CurrencyRate convertToEntity(CurrencyRateDTO dto) {
        CurrencyRate entity = new CurrencyRate();
        entity.setCode(dto.getCode());
        entity.setCurrDate(LocalDate.parse(dto.getCurrDate()));
        entity.setNameBg(dto.getNameBg());
        entity.setNameEn(dto.getNameEn());
        entity.setRate(dto.getRate());
        entity.setRatio(dto.getRatio());
        entity.setReverseRate(dto.getReverseRate());
        return entity;
    }
}