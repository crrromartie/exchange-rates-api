package ru.clevertec.rates.controller.exception;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.rates.exception.CurrencyException;
import ru.clevertec.rates.exception.ExceptionMessageKey;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Locale;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdviceController {
    private final ExceptionMessageCreator exceptionMessageCreator;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CurrencyException.class)
    public ExceptionResponse handleCurrencyException(CurrencyException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator
                .createMessageWithParameter(e.getExceptionMessageKey(), e.getParameter(), locale);
        log.error(exceptionMessage);
        return ExceptionResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(ExceptionCodeConstant.INCORRECT_CURRENCY)
                .errorMessage(exceptionMessage)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder exceptionMessage = new StringBuilder();
        e.getConstraintViolations()
                .forEach(constraintViolation -> exceptionMessage.append(constraintViolation.getMessage()).append(";"));
        log.error(exceptionMessage.toString());
        return ExceptionResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(ExceptionCodeConstant.NOT_VALID_PARAMETER)
                .errorMessage(exceptionMessage.toString())
                .details(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator
                .createMessageWithParameter(ExceptionMessageKey.MISSING_REQUEST_PARAMETER, e.getParameterName(), locale);
        log.error(exceptionMessage);
        return ExceptionResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(ExceptionCodeConstant.MISSING_REQUEST_PARAMETER)
                .errorMessage(exceptionMessage)
                .details(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator
                .createMessageWithoutParameter(ExceptionMessageKey.METHOD_NOT_ALLOWED, locale);
        log.error(exceptionMessage);
        return ExceptionResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(ExceptionCodeConstant.METHOD_NOT_ALLOWED)
                .errorMessage(exceptionMessage)
                .details(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ExceptionResponse handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator
                .createMessageWithoutParameter(ExceptionMessageKey.INCORRECT_MEDIA_TYPE, locale);
        log.error(exceptionMessage);
        return ExceptionResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(ExceptionCodeConstant.INCORRECT_MEDIA_TYPE)
                .errorMessage(exceptionMessage)
                .details(e.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public ExceptionResponse handleFeignException(
            FeignException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator
                .createMessageWithoutParameter(ExceptionMessageKey.EXTERNAL_SERVER_ERROR, locale);
        log.error(exceptionMessage);
        return ExceptionResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(e.status())
                .errorMessage(exceptionMessage)
                .details(String.join(";", e.getLocalizedMessage(), e.contentUTF8()))
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ExceptionResponse handleRuntimeException(RuntimeException e, Locale locale) {
        String exceptionMessage = exceptionMessageCreator
                .createMessageWithoutParameter(ExceptionMessageKey.INTERNAL_SERVER_ERROR, locale);
        log.error(exceptionMessage);
        return ExceptionResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(ExceptionCodeConstant.INTERNAL_SERVER_ERROR)
                .errorMessage(exceptionMessage)
                .details(e.getLocalizedMessage())
                .build();
    }
}
