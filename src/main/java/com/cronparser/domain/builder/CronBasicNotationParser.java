package com.cronparser.domain.builder;

import com.cronparser.domain.CronTimeUnit;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Stream;

public class CronBasicNotationParser implements CronNotationParser {

    @Override
    public boolean appliesTo(String input) {
        return split(input).allMatch(StringUtil::isInt);
    }

    @Override
    public int[] toValues(String input, CronTimeUnit unit) {
        int[] values = toIntegers(input);
        unit.validate(values);
        return values;
    }

    private static int[] toIntegers(String input) {
        return split(input).mapToInt(CronBasicNotationParser::toInteger).toArray();
    }

    private static int toInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CronInvalidNotationException(input, e);
        }
    }

    private static Stream<String> split(String input) {
        return Arrays.stream(StringUtils.split(input, ","));
    }

}
