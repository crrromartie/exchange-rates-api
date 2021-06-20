package ru.clevertec.rates.controller.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionCodeConstant {
    public static final int NOT_VALID_PARAMETER = 40001;
    public static final int INCORRECT_CURRENCY = 40002;
    public static final int MISSING_REQUEST_PARAMETER = 40003;
    public static final int METHOD_NOT_ALLOWED = 40501;
    public static final int INCORRECT_MEDIA_TYPE = 41501;
    public static final int INTERNAL_SERVER_ERROR = 50001;
}
