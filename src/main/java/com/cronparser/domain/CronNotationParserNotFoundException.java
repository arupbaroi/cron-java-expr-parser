package com.cronparser.domain;

public class CronNotationParserNotFoundException extends CronParserException {

    public CronNotationParserNotFoundException(String value) {
        super(String.format("notation parser not found for value %s", value));
    }

}
