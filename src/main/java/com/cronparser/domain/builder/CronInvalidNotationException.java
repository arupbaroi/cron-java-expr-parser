package com.cronparser.domain.builder;

import com.cronparser.domain.CronParserException;

public class CronInvalidNotationException extends CronParserException {

    public CronInvalidNotationException(String value, Throwable cause) {
        super(value, cause);
    }

    public CronInvalidNotationException(String value) {
        super(value);
    }

}
