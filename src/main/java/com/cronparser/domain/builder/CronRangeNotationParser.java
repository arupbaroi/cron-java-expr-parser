package com.cronparser.domain.builder;

import com.cronparser.domain.CronTimeUnit;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

import static com.cronparser.domain.builder.StringUtil.isInt;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class CronRangeNotationParser implements CronNotationParser {

    @Override
    public boolean appliesTo(String value) {
        String[] parts = split(value);
        if (parts.length == 2) {
            return isInt(parts[0]) && isInt(parts[1]);
        }
        return false;
    }

    @Override
    public int[] toValues(String input, CronTimeUnit unit) {
        try {
            String[] parts = split(input);
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            unit.validate(start, end);
            return IntStream.rangeClosed(min(start, end), max(start, end)).toArray();
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new CronInvalidNotationException(input, e);
        }
    }

    private static String[] split(String value) {
        return StringUtils.split(value, "-");
    }

}