package ru.clevertec.rates.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.clevertec.rates.client.GifClient;
import ru.clevertec.rates.client.RateClient;
import ru.clevertec.rates.entity.GifData;
import ru.clevertec.rates.exception.CurrencyException;
import ru.clevertec.rates.exception.ExceptionMessageKey;
import ru.clevertec.rates.service.RateService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {
    private final RateClient rateClient;
    private final GifClient gifClient;

    @Override
    public GifData checkCourse(String currency) {
        BigDecimal latestRate = rateClient.getLatestRate().getRates().entrySet().stream()
                .filter(e -> e.getKey().equals(currency.toUpperCase()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new CurrencyException(ExceptionMessageKey.CURRENCY_INCORRECT, currency));
        BigDecimal yesterdayRate = rateClient.getYesterdayRate(
                LocalDate.now().minus(Period.ofDays(1)).toString())
                .getRates().entrySet().stream()
                .filter(e -> e.getKey().equals(currency.toUpperCase()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new CurrencyException(ExceptionMessageKey.CURRENCY_INCORRECT, currency));
        log.info("base {}, currency {}, latest_rate {}, yesterday_rate {}",
                rateClient.getLatestRate().getBase(), currency.toUpperCase(), latestRate, yesterdayRate);
        return latestRate.compareTo(yesterdayRate) > 0 ? gifClient.getBrokeGif() : gifClient.getRichGif();
    }
}
