package ru.clevertec.rates.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessageKey {
    public static final String CURRENCY_INCORRECT = "incorrect_currency";
    public static final String CURRENCY_INCORRECT_NAME = "{incorrect_currency_name}";
    public static final String MISSING_REQUEST_PARAMETER = "missing_request_parameter";
    public static final String METHOD_NOT_ALLOWED = "method_not_allowed";
    public static final String INCORRECT_MEDIA_TYPE = "incorrect_media_type";
    public static final String INTERNAL_SERVER_ERROR = "internal_server_error";
    public static final String EXTERNAL_SERVER_ERROR = "external_service_error";
}
