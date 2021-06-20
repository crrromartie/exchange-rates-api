package ru.clevertec.rates.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrencyException extends RuntimeException {
    private final String exceptionMessageKey;
    private final Object parameter;
}
