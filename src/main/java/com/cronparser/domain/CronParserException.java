package com.cronparser.domain;

public class CronParserException extends RuntimeException {

    public CronParserException(String message) {
        super(message);
    }

    public CronParserException(String message, Throwable cause) {
        super(message, cause);
    }

}
