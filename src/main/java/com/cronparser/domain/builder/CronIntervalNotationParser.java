package com.cronparser.domain.builder;

import com.cronparser.domain.CronTimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static com.cronparser.domain.builder.StringUtil.isInt;

@RequiredArgsConstructor
public class CronIntervalNotationParser implements CronNotationParser {

    private static final String WILDCARD = "*";

    private final CronRangeNotationParser rangeParser;
    private final CronBasicNotationParser basicParser;

    public CronIntervalNotationParser() {
        this(new CronRangeNotationParser(), new CronBasicNotationParser());
    }

    @Override
    public boolean appliesTo(String value) {
        String[] parts = split(value);
        if (parts.length == 2) {
            return isIntWildcardRangeOrIntegers(parts[0]) && isInt(parts[1]);
        }
        return false;
    }

    @Override
    public int[] toValues(String input, CronTimeUnit unit) {
        try {
            String[] parts = split(input);
            String first = parts[0];
            int start = toStart(first, unit);
            int end = toEnd(first, unit);
            unit.validate(start);
            unit.validate(end);
            int interval = Integer.parseInt(parts[1]);
            if (interval == 0) {
                throw new CronInvalidNotationException("Invalid interval for "+unit.formattedName(), new Throwable());
            }
            return calculateIntervalsArray(start, end, interval);
        } catch (ArrayIndexOutOfBoundsException | CronInvalidNotationException e) {
            throw new CronInvalidNotationException(input, e);
        }
    }

    private static String[] split(String value) {
        return StringUtils.split(value, "/");
    }

    private int toStart(String value, CronTimeUnit unit) {
        if (WILDCARD.equals(value)) {
            return unit.getLowerBound();
        }
        if (rangeParser.appliesTo(value)) {
            return rangeParser.toFirstValue(value, unit);
        }
        return basicParser.toFirstValue(value, unit);
    }

    private int toEnd(String value, CronTimeUnit unit) {
        if (rangeParser.appliesTo(value)) {
            int [] values = rangeParser.toValues(value, unit);
            return values[values.length-1];
        }
        return unit.getUpperBound();
    }

    private static int[] calculateIntervalsArray(int start, int end, int interval) {
        return calculateIntervals(start, end, interval).distinct().sorted().toArray();
    }

    private static IntStream calculateIntervals(int start, int end, int interval) {
        List<Integer> values = new ArrayList<>();
        for (int i = start; i <= end; i += interval) {
            values.add(i);
        }
        return values.stream().mapToInt(Integer::intValue);
    }

    private static IntPredicate lessThan(int max) {
        return i -> i <= max;
    }

    private static IntUnaryOperator incrementBy(int interval) {
        return i -> i + interval;
    }

    private boolean isIntWildcardRangeOrIntegers(String value) {
        return WILDCARD.equals(value) ||
                rangeParser.appliesTo(value) ||
                basicParser.appliesTo(value);
    }

}
