package com.bnbcurrencyservice.consumer.repository;

import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
}
