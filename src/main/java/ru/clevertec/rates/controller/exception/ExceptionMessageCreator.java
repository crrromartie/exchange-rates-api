package ru.clevertec.rates.controller.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class ExceptionMessageCreator {
    private final MessageSource messageSource;

    public String createMessageWithParameter(String exceptionMessageKey, Object exceptionParameter, Locale locale) {
        String exceptionMessage = messageSource.getMessage(exceptionMessageKey, new Object[]{}, locale);
        return String.format(exceptionMessage, exceptionParameter);
    }

    public String createMessageWithoutParameter(String exceptionMessageKey, Locale locale) {
        return messageSource.getMessage(exceptionMessageKey, new Object[]{}, locale);
    }
}
