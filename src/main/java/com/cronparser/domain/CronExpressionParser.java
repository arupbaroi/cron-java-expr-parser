package com.cronparser.domain;

import com.cronparser.domain.builder.CronHelperNotationParser;
import com.cronparser.domain.builder.CronNotationParser;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CronExpressionParser {

    private final CronNotationParser notationParser;

    public CronExpressionParser() {
        this(new CronHelperNotationParser());
    }

    public CronResult parse(String[] values) {
        return CronResult.builder()
                .minutes(parse(values, CronTimeUnit.MINUTES))
                .hours(parse(values, CronTimeUnit.HOURS))
                .daysOfMonth(parse(values, CronTimeUnit.DAYS_OF_MONTH))
                .months(parse(values, CronTimeUnit.MONTHS))
                .daysOfWeek(parse(values, CronTimeUnit.DAYS_OF_WEEK))
                .command(values[5])
                .build();
    }

    private int[] parse(String[] values, CronTimeUnit timeUnit) {
        String rawValues = values[timeUnit.ordinal()];
        String intValues = timeUnit.toIntValues(rawValues);
        if (notationParser.appliesTo(intValues)) {
            return notationParser.toValues(intValues, timeUnit);
        }
        throw new CronNotationParserNotFoundException(rawValues);
    }

}
