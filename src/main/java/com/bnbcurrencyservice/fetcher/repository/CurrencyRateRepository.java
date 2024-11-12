package com.bnbcurrencyservice.fetcher.repository;

import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findFirstByOrderByDownloadTimeDesc();
}
