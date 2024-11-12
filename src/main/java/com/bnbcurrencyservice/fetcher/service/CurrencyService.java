package com.bnbcurrencyservice.fetcher.service;

import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import com.bnbcurrencyservice.consumer.model.CurrencyRateDTO;
import com.bnbcurrencyservice.fetcher.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRateRepository repository;
    private final XmlParserService xmlParserService;
    private final SimpMessagingTemplate websocket;
    private final RestTemplate restTemplate;

    private static final String BNB_URL = "https://www.bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/index.htm?download=xml&search=&lang=BG";

    @Transactional
    public void downloadAndProcessCurrencies() {
        try {
            String xmlData = downloadXmlData();
            List<CurrencyRate> newRates = xmlParserService.parseXmlData(xmlData);

            if (hasRatesChanged(newRates)) {
                log.info("New currency rates detected. Saving {} rates", newRates.size());
                repository.saveAll(newRates);

                List<CurrencyRateDTO> dtos = newRates.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());

                websocket.convertAndSend("/topic/currencies", dtos);
                log.info("Currency rates sent via WebSocket");
            } else {
                log.info("No changes in currency rates");
            }
        } catch (Exception e) {
            log.error("Error processing currencies", e);
            throw new RuntimeException("Failed to process currencies", e);
        }
    }

    private String downloadXmlData() {
        return restTemplate.getForObject(BNB_URL, String.class);
    }

    private boolean hasRatesChanged(List<CurrencyRate> newRates) {
        if (newRates.isEmpty()) {
            return false;
        }

        Optional<CurrencyRate> lastRate = repository.findFirstByOrderByDownloadTimeDesc();
        if (lastRate.isEmpty()) {
            return true;
        }

        CurrencyRate oldRate = lastRate.get();
        CurrencyRate newRate = newRates.get(0);

        return !oldRate.getCurrDate().equals(newRate.getCurrDate()) ||
                !oldRate.getRate().equals(newRate.getRate());
    }

    private CurrencyRateDTO convertToDto(CurrencyRate rate) {
        CurrencyRateDTO dto = new CurrencyRateDTO();
        dto.setCode(rate.getCode());
        dto.setCurrDate(rate.getCurrDate().toString());
        dto.setNameBg(rate.getNameBg());
        dto.setNameEn(rate.getTitle());
        dto.setRate(rate.getRate());
        dto.setRatio(rate.getRatio());
        dto.setReverseRate(rate.getReverseRate());
        return dto;
    }
}