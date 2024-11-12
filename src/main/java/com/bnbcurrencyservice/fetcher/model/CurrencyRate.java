package com.bnbcurrencyservice.fetcher.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "currency_rates")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private LocalDate currDate;
    private String extraInfo;
    private String fStar;
    private String gold;
    private String nameBg;
    private String nameEn;
    private String title;
    private BigDecimal rate;
    private Integer ratio;
    private BigDecimal reverseRate;
    private LocalDateTime downloadTime;
}