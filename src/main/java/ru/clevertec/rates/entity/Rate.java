package ru.clevertec.rates.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@Builder
@Data
public class Rate implements Serializable {
    private String base;
    private Map<String, BigDecimal> rates;
}
