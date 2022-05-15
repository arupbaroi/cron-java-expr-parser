package com.cronparser.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
public enum CronTimeUnit {

    MINUTES(0, 59),
    HOURS(0, 23),
    DAYS_OF_MONTH(1, 31),
    MONTHS(1, 12),
    DAYS_OF_WEEK(1, 7);

    private final int lowerBound;
    private final int upperBound;

    public int[] allValues() {
        return IntStream.rangeClosed(lowerBound, upperBound).toArray();
    }

    public void validate(int... values) {
        Arrays.stream(values).forEach(this::validate);
    }

    public String toIntValues(String rawValues) {
        String result = "";
        switch(this) {
            case DAYS_OF_WEEK :
                result = toDaysOfWeek(rawValues);
                break;
            case MONTHS :
                result = toMonths(rawValues);
                break;
            default :
                result = rawValues;
        }
        return result;
    }

    public void validate(int value) {
        if (isOutOfBounds(value)) {
            throw new CronNotationOutOfBoundsException(value, this);
        }
    }

    public String formattedName() {
        return name().toLowerCase().replace("_", " ");
    }

    private boolean isOutOfBounds(int value) {
        return value < lowerBound || value > upperBound;
    }

    private static String toDaysOfWeek(String rawValues) {
        return rawValues.toUpperCase()
                .replace("MON", "1")
                .replace("TUE", "2")
                .replace("WED", "3")
                .replace("THU", "4")
                .replace("FRI", "5")
                .replace("SAT", "6")
                .replace("SUN", "7");
    }

    private static String toMonths(String rawValues) {
        return rawValues.toUpperCase()
                .replace("JAN", "1")
                .replace("FEB", "2")
                .replace("MAR", "3")
                .replace("APR", "4")
                .replace("MAY", "5")
                .replace("JUN", "6")
                .replace("JUL", "7")
                .replace("AUG", "8")
                .replace("SEP", "9")
                .replace("OCT", "10")
                .replace("NOV", "11")
                .replace("DEC", "12");
    }

}
