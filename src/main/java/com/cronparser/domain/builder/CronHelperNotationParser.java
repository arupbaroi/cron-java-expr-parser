package com.cronparser.domain.builder;

import com.cronparser.domain.CronTimeUnit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class CronHelperNotationParser implements CronNotationParser {

    private final Collection<CronNotationParser> parsers;

    public CronHelperNotationParser() {
        this(new CronWildcardNotationParser(),
                new CronRangeNotationParser(),
                new CronIntervalNotationParser(),
                new CronBasicNotationParser()
        );
    }

    public CronHelperNotationParser(CronNotationParser... parsers) {
        this(Arrays.asList(parsers));
    }

    @Override
    public boolean appliesTo(String value) {
        return toSegments(value).stream().allMatch(this::appliesToSegment);
    }

    @Override
    public int[] toValues(String input, CronTimeUnit unit) {
        return toSegments(input).stream()
                .flatMapToInt(segment -> segmentToValues(segment, unit))
                .sorted()
                .toArray();
    }

    private Collection<String> toSegments(String value) {
        return Arrays.asList(StringUtils.split(value, ","));
    }

    private boolean appliesToSegment(String segment) {
        return parsers.stream().anyMatch(parser -> parser.appliesTo(segment));
    }

    private IntStream segmentToValues(String segment, CronTimeUnit unit) {
        return parsers.stream().filter(parser -> parser.appliesTo(segment))
                .map(parser -> parser.toValues(segment, unit))
                .map(IntStream::of)
                .flatMapToInt(Function.identity());
    }

}