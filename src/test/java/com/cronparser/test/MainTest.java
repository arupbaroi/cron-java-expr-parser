package com.cronparser.test;

import com.cronparser.Main;
import com.cronparser.domain.CronResult;
import com.cronparser.domain.CronResultFormatter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.webcompere.systemstubs.SystemStubs.tapSystemErrAndOut;

class MainTest {

    private final CronResultFormatter formatter = new CronResultFormatter();

    @Test
    void shouldParseValidCronExpression() throws Exception {
        String[] args = {"*/15 0 1,15 * 1-5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        CronResult result = buildResult();
        String formatted = formatter.format(result);

        assertThat(output).contains(formatted);
    }

    @Test
    void shouldParseValidMinutesIntervalWithCommaStartValuesCronExpression() throws Exception {
        String[] args = {"3,45/15 0 1,15 * 1-5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("minute        3 45");
    }

    @Test
    void shouldParseValidMinutesIntervalWithCommaAndRangeStartValuesCronExpression() throws Exception {
        String[] args = {"3,40-45/5 0 1,15 * 1-5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("minute        3 40 45");
    }

    @Test
    void shouldParseValidComplexMinutesCronExpression() throws Exception {
        String[] args = {"1,4-8,*/15 0 1,15 * 1-5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("minute        0 1 4 5 6 7 8 15 30 45");
    }

    @Test
    void shouldParseValidTextualDaysOfWeekCronExpression() throws Exception {
        String[] args = {"1,4-8,*/15 0 1,15 * Mon-Fri /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("day of week   1 2 3 4 5");
    }

    @Test
    void shouldParseValidTextualMonthsCronExpression() throws Exception {
        String[] args = {"1,4-8,*/15 0 1,15 jan,jun,dec 1-5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("month         1 6 12");
    }

    @Test
    void shouldPrintUsageIfNoExpressionPassed() throws Exception {
        String[] args = new String[0];

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("usage: please provide a valid cron expression");
    }

    @Test
    void shouldPrintErrorIfLessThanSixArgumentsProvided() throws Exception {
        String[] args = {"59 0 1,15 * 1-5"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("usage: please provide a valid cron expression, " +
                "invalid cron expression provided 59 0 1,15 * 1-5");
    }

    @Test
    void shouldPrintErrorIfValueIsOutOfBounds() throws Exception {
        String[] args = {"60 0 1,15 * 1-5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("invalid minutes value 60, outside bounds 0 and 59");
    }

    @Test
    void shouldPrintErrorIfNotationParserNotFoundForSimpleDecimalValue() throws Exception {
        String[] args = {"3.5 0 1,15 * 1-5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("notation parser not found for value 3.5");
    }

    @Test
    void shouldPrintErrorIfNotationParserNotFoundForIntervalWithDecimalValue() throws Exception {
        String[] args = {"3 0 1,15 * */2.5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("notation parser not found for value */2.5");
    }

    @Test
    void shouldPrintErrorIfNotationParserNotFoundForRangeWithDecimalValue() throws Exception {
        String[] args = {"3 0 1,15 * 1-5.5 /usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("notation parser not found for value 1-5.5");
    }

    private static CronResult buildResult() {
        return CronResult.builder()
                .minutes(new int[]{45, 30, 15, 0})
                .hours(new int[]{0})
                .daysOfMonth(new int[]{15, 1})
                .months(new int[]{12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1})
                .daysOfWeek(new int[]{5, 4, 3, 2, 1})
                .command("/usr/bin/find")
                .build();
    }

}

