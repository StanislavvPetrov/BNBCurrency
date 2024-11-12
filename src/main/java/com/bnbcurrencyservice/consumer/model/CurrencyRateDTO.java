package com.bnbcurrencyservice.consumer.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CurrencyRateDTO {
    private String code;
    private String currDate;
    private String nameBg;
    private String nameEn;
    private BigDecimal rate;
    private Integer ratio;
    private BigDecimal reverseRate;
}
§