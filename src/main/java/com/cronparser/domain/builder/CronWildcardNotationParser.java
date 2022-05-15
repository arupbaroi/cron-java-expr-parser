package com.cronparser.domain.builder;

import com.cronparser.domain.CronTimeUnit;

public class CronWildcardNotationParser implements CronNotationParser {

    @Override
    public boolean appliesTo(String value) {
        return value.equals("*");
    }

    @Override
    public int[] toValues(String input, CronTimeUnit unit) {
        return unit.allValues();
    }

}
