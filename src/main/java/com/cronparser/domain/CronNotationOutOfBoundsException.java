package com.cronparser.domain;

public class CronNotationOutOfBoundsException extends CronParserException {

    public CronNotationOutOfBoundsException(int value, CronTimeUnit unit) {
        this(Integer.toString(value), unit);
    }

    public CronNotationOutOfBoundsException(String value, CronTimeUnit unit) {
        super(String.format("invalid %s value %s, outside bounds %d and %d",
                unit.formattedName(),
                value,
                unit.getLowerBound(),
                unit.getUpperBound()));
    }

}
