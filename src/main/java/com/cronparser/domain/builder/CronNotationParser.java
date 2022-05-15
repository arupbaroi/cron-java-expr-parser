package com.cronparser.domain.builder;

import com.cronparser.domain.CronTimeUnit;

public interface CronNotationParser {

    boolean appliesTo(String value);

    int[] toValues(String input, CronTimeUnit unit);

    default int toFirstValue(String input, CronTimeUnit unit) {
        return toValues(input, unit)[0];
    }

}
