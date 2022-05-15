package com.cronparser.test;

import com.cronparser.domain.CronResult;
import com.cronparser.domain.CronResultFormatter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CronResultFormatterTest {

    private final CronResultFormatter formatter = new CronResultFormatter();

    @Test
    void shouldFormatResult() {
        CronResult result = buildResult();

        String formatted = formatter.format(result);

        /*
        StringBuffer b = new StringBuffer();
        b.append(format("%-14s%s\n", "minute", "39 40"));
        b.append(format("%-14s%s\n", "hour", "9 10"));
        b.append(format("%-14s%s\n", "day of month", "20 21"));
        b.append(format("%-14s%s\n", "month", "3 4"));
        b.append(format("%-14s%s\n", "day of week", "1 2"));
        b.append(format("%-14s%s\n", "command", "my-command"));
        */

        assertThat(formatted).contains("minute        39 40");
        assertThat(formatted).contains("hour          9 10");
        assertThat(formatted).contains("day of month  20 21");
        assertThat(formatted).contains("month         3 4");
        assertThat(formatted).contains("day of week   1 2");
        assertThat(formatted).contains("command       my-command");


    }

    private static CronResult buildResult() {
        return CronResult.builder()
                .minutes(new int[]{40, 39})
                .hours(new int[]{10, 9})
                .daysOfMonth(new int[]{21, 20})
                .months(new int[]{4, 3})
                .daysOfWeek(new int[]{2, 1})
                .command("my-command")
                .build();
    }

}
