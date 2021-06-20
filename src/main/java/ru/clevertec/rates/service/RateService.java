package ru.clevertec.rates.service;

import org.springframework.validation.annotation.Validated;
import ru.clevertec.rates.entity.GifData;
import ru.clevertec.rates.exception.ExceptionMessageKey;

import javax.validation.constraints.Pattern;

@Validated
public interface RateService {

    GifData checkCourse(@Pattern(regexp = "[A-z]{3}", message = ExceptionMessageKey.CURRENCY_INCORRECT_NAME) String currency);
}
